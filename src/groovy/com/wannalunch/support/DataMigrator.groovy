package com.wannalunch.support

import groovy.sql.Sql;

import com.wannalunch.domain.Lunch.PaymentOption;

import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

import com.wannalunch.domain.Comment;
import com.wannalunch.domain.Lunch;
import com.wannalunch.domain.User;

class DataMigrator {

//  def sourceDbHost = "external-db.s79782.gridserver.com"
  def sourceDbHost = "127.0.0.1"
  def sourceDbPort = "3306"
  def sourceDbName = "db79782_wannalunch_test"
  def sourceDbUser = "db79782_lunch"
  def sourceDbPasswd = "lendRebane7"

  def sql

  def dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")

  def log = Logger.getLogger(getClass())

  def users = [:]
  def lunches = [:]
  def comments = [:]

  void migrateAll() {
    sql = Sql.newInstance("jdbc:mysql://${sourceDbHost}:${sourceDbPort}/${sourceDbName}",
        sourceDbUser, sourceDbPasswd, "com.mysql.jdbc.Driver")

    String info = "Starting database migration... " +
                  "Migrating data from database: ${sourceDbHost}:${sourceDbPort}/${sourceDbName}. " +
                  "Please make sure the database contains no data"

    log.info info

    migrateUsers()
    migrateLunches()
//    migrateComments()
  }

  private void migrateUsers() {
    sql.eachRow("select * from twitter_users") {
      def user = new User(
          name: it.fullname,
          username: it.user,
          facebookProfile: it.fb_url,
          linkedInProfile: it.linkedin_url)

      users[it.user] = user
      save(user)
    }
  }

  private void migrateLunches() {
    def createdStartDate = new LocalDateTime(2010, 04, 23, 12, 00)
    def rowId = 0

    sql.eachRow("select * from lunches order by id") {
      PaymentOption paymentOption;

      switch (Integer.parseInt(it.payment)) {
        case 1:
          paymentOption = PaymentOption.I_PAY
          break;
        case 2:
          paymentOption = PaymentOption.YOU_PAY
          break;
        case 3:
          paymentOption = PaymentOption.WE_SPLIT
          break;
        default:
          throw new IllegalArgumentException("Unknown payment type: " + it.payment)
      }

      def lunch = new Lunch(
          topic: it.topic,
          description: it.desc,
          createdDateTime: createdStartDate.plusMinutes(10 * rowId++),
          date: new LocalDate(it.lunchdate),
          time: new LocalTime(it.lunchtime),
          location: it.place,
          creator: users[it.user],
          paymentOption: paymentOption)

      lunches[it.id] = lunch
      save(lunch)
    }
  }

  private void migrateComments() {
    sql.eachRow("select * from comments") {
    }
  }

  private void save(entity) {
    assert entity.save(flush: true), entity.errors
  }

}
