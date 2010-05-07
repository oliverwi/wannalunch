// sample: groovy -cp posgresql-8.4-701.jdbc4.jar:joda-time-1.6.jar lunchers_migration.groovy postgres passwd

import groovy.sql.Sql

def sql = Sql.newInstance("jdbc:postgresql://localhost/wannalunch", args[0], args[1], "org.postgresql.Driver")

sql.withTransaction {
  
  println 'Starting lunchers migration'
  
  println 'Creating lunch_wl_user relation'
  sql.execute("create table lunch_wl_user (" +
  		  "lunch_applicants_id      bigint," +
  		  "user_id                 bigint," +
  		  "lunch_participants_id    bigint," +
  		  "foreign key (lunch_applicants_id) references lunch(id)," +
  		  "foreign key (user_id) references wl_user(id)," +
  		  "foreign key (lunch_participants_id) references lunch(id)" +
  		");")
  
  println 'Migrating data from lunch_luncher to lunch_wl_user'
  sql.eachRow("select lunch_applicant_id, luncher_id, lunch_participant_id from lunch_luncher") {
    def luncherRow = sql.firstRow("select id, wants_notification, user_id from luncher where id = ?", [it.luncher_id])
    
    sql.execute('insert into lunch_wl_user (lunch_applicants_id, user_id, lunch_participants_id) values (?, ?, ?)', 
        [it.lunch_applicant_id, luncherRow.user_id, it.lunch_participant_id])
  }
  
  println 'Dropping table lunch_luncher'
  sql.execute("drop table lunch_luncher")
  
  println 'Adding column creator_wants_notifications to lunch class'
  sql.execute("alter table lunch add column creator_wants_notifications boolean")
  sql.execute("update lunch set creator_wants_notifications = ?", [true])
  sql.execute("alter table lunch alter column creator_wants_notifications set not null")
  
  println 'Renaming column lunch_creator_id to creator_id on relation lunch'
  sql.execute("alter table lunch rename column lunch_creator_id to creator_id")
  
  println 'Dropping table luncher'
  sql.execute("drop table luncher")
  
  println ''
  desc(sql, "lunch_wl_user")
  
  println ''
  desc(sql, "lunch")
  
  println 'select * from lunch_wl_user'
  sql.eachRow('select * from lunch_wl_user') {
    println it
  }
  
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