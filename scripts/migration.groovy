// sample: groovy -cp posgresql-8.4-701.jdbc4.jar:joda-time-1.6.jar migration.groovy postgres ohlala

import groovy.sql.Sql

def sql = Sql.newInstance("jdbc:postgresql://localhost/wannalunch", args[0], args[1], "org.postgresql.Driver")

sql.withTransaction {

  println 'dropping wl_user_email_key'
  sql.execute("alter table wl_user drop constraint wl_user_email_key")

  println ''
  desc(sql, "wl_user")

  // lunches

  println ''
  println 'migrating lunches'

  sql.execute('alter table lunch add column create_date_time_tmp timestamp')
  sql.execute('alter table lunch add column date_tmp date')
  sql.execute('alter table lunch add column time_tmp time')

  sql.eachRow("select id, date, time, create_date_time from lunch") {
    def date = new java.sql.Date(new ObjectInputStream(new ByteArrayInputStream(it.date)).readObject().toDateTimeAtStartOfDay().millis)
    def time = new java.sql.Time(new ObjectInputStream(new ByteArrayInputStream(it.time)).readObject().toDateTimeToday().withZone(org.joda.time.DateTimeZone.UTC).millis)
    def createDateTime = new java.sql.Timestamp(new ObjectInputStream(new ByteArrayInputStream(it.create_date_time)).readObject().toDateTime().millis)

    sql.execute('update lunch set date_tmp = ?, time_tmp = ?, create_date_time_tmp = ? where id = ?', [date, time, createDateTime, it.id])
  }

  sql.execute('alter table lunch drop column date')
  sql.execute('alter table lunch drop column time')
  sql.execute('alter table lunch drop column create_date_time')

  sql.execute('alter table lunch rename column date_tmp to date')
  sql.execute('alter table lunch rename column time_tmp to time')
  sql.execute('alter table lunch rename column create_date_time_tmp to create_date_time')

  sql.execute('alter table lunch alter column date set not null')
  sql.execute('alter table lunch alter column time set not null')
  sql.execute('alter table lunch alter column create_date_time set not null')

  // comments

  println ''
  println 'migrating comments'

  sql.execute('alter table comment add column date_tmp date')
  sql.execute('alter table comment add column time_tmp time')

  sql.eachRow("select id, date, time from comment") {
    def date = new java.sql.Date(new ObjectInputStream(new ByteArrayInputStream(it.date)).readObject().toDateTimeAtStartOfDay().millis)
    def time = new java.sql.Time(new ObjectInputStream(new ByteArrayInputStream(it.time)).readObject().toDateTimeToday().withZone(org.joda.time.DateTimeZone.UTC).millis)

    sql.execute('update comment set date_tmp = ?, time_tmp = ? where id = ?', [date, time, it.id])
  }

  sql.execute('alter table comment drop column date')
  sql.execute('alter table comment drop column time')

  sql.execute('alter table comment rename column date_tmp to date')
  sql.execute('alter table comment rename column time_tmp to time')

  sql.execute('alter table comment alter column date set not null')
  sql.execute('alter table comment alter column time set not null')
  
  println ''
  println 'creating table city'
  sql.execute("create table city (" +
  		  "id bigint not null primary key," +
  		  "version bigint not null," +
  		  "name varchar(255) not null unique" +
  		");")
  
  println ''
  println 'inserting data on relation city'
  sql.execute("insert into city(id, version, name) values (?, ?, ?)", [1, 0, 'Tallinn'])
  sql.execute("insert into city(id, version, name) values (?, ?, ?)", [2, 0, 'Tartu'])
  sql.execute("insert into city(id, version, name) values (?, ?, ?)", [3, 0, 'World'])
  
  println ''
  println 'adding column city_id to relation lunch'
  sql.execute("alter table lunch add column city_id bigint")
  sql.execute("alter table lunch add constraint fk628c32ae77c2c76 foreign key (city_id) references city(id)")
  
  println ''
  println 'assigning all current lunches to Tallinn'
  sql.execute("update lunch set city_id = ?", [1])
  
  println ''
  println 'adding not null constraint to city_id on relation lunch'
  sql.execute('alter table lunch alter column city_id set not null')
  
  println ''
  desc(sql, "city")
  
  println ''
  desc(sql, "lunch")
  
  println ''
  desc(sql, "comment")

  println ''
  println 'select * from lunch'

  sql.eachRow('select * from lunch') {
    println it
  }

  println ''
  println 'select * from comment'

  sql.eachRow('select * from comment') {
    println it
  }

  println ''

  if (args.find { it == 'commit' }) {
    println "committing transaction"
    sql.commit()
  } else {
    println "rolling transaction back"
    sql.rollback()
  }
}

void desc(sql, table, schema = "public") {
  println "$schema: $table"
  sql.eachRow(
      """
      select
        f.attnum as number,
        f.attname as name,
        f.attnum,
        f.attnotnull as notnull,
        pg_catalog.format_type(f.atttypid, f.atttypmod) as type,
        case when p.contype = 'p' then 'true' else 'false' end as primarykey,
        case when p.contype = 'u' then 'true' else 'false' end as uniquekey,
        case when p.contype = 'f' then g.relname end as foreignkey,
        case when p.contype = 'f' then p.confkey end as foreignkey_fieldnum,
        case when p.contype = 'f' then g.relname end as foreignkey,
        case when p.contype = 'f' then p.conkey end as foreignkey_connnum,
        case when f.atthasdef = 't' then d.adsrc end as default
      from pg_attribute f
        join pg_class c on c.oid = f.attrelid
        join pg_type t on t.oid = f.atttypid
        left join pg_attrdef d on d.adrelid = c.oid
          and d.adnum = f.attnum
        left join pg_namespace n on n.oid = c.relnamespace
        left join pg_constraint p on p.conrelid = c.oid
          and f.attnum = any (p.conkey)
        left join pg_class as g on p.confrelid = g.oid
      where c.relkind = 'r'
        and n.nspname = ?
        and c.relname = ?
        and f.attnum > 0
      order by number
      """, [schema, table]) {
    println it
  }
}