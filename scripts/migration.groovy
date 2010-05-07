// sample: groovy -cp posgresql-8.4-701.jdbc4.jar:joda-time-1.6.jar migration.groovy postgres ohlala

import groovy.sql.Sql

def sql = Sql.newInstance("jdbc:postgresql://localhost/wannalunch", args[0], args[1], "org.postgresql.Driver")

sql.withTransaction {

//  sql.execute("alter table wl_user alter column drop constraint 'wl_user_email_key'")

  sql.execute('alter table lunch add column create_date_time_tmp timestamp')
  sql.execute('alter table lunch add column date_tmp date')
  sql.execute('alter table lunch add column time_tmp time')

  sql.eachRow("select id, date, time, create_date_time from lunch") {
    def date = new java.sql.Date(new ObjectInputStream(new ByteArrayInputStream(it.date)).readObject().toDateTimeAtStartOfDay().millis)
    def time = new java.sql.Time(new ObjectInputStream(new ByteArrayInputStream(it.time)).readObject().toDateTimeToday().withZone(org.joda.time.DateTimeZone.UTC).millis)
    def createDateTime = new java.sql.Timestamp(new ObjectInputStream(new ByteArrayInputStream(it.create_date_time)).readObject().toDateTime().millis)

    println time

    sql.execute('update lunch set date_tmp = ?, time_tmp = ?, create_date_time_tmp = ? where id = ?', date, time, createDateTime, it.id)
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

  if (args.find { 'commit' }) {
    println "found"
//    sql.commit()
  } else {
    sql.rollback()
  }
}
