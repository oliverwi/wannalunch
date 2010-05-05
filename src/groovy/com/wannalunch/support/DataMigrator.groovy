package com.wannalunch.support

import groovy.sql.Sql;

import com.wannalunch.domain.Lunch.PaymentOption;

import org.apache.log4j.Logger;
import org.codehaus.groovy.grails.commons.ConfigurationHolder;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

import com.wannalunch.domain.Comment;
import com.wannalunch.domain.Lunch;
import com.wannalunch.domain.Luncher;
import com.wannalunch.domain.User;

class DataMigrator {

  def sourceDbHost = "external-db.s79782.gridserver.com"
  def sourceDbPort = "3306"
  def sourceDbName = "db79782_wp_wannalunch"
  def sourceDbUser = "db79782_lunch"
  def sourceDbPasswd = "lendRebane7"

  def config = ConfigurationHolder.config

  def sql

  def dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")

  def log = Logger.getLogger(getClass())

  def users = [:]
  def lunches = [:]

  void migrateAll() {
    sql = Sql.newInstance("jdbc:mysql://${sourceDbHost}:${sourceDbPort}/${sourceDbName}?zeroDateTimeBehavior=convertToNull",
        sourceDbUser, sourceDbPasswd, "com.mysql.jdbc.Driver")

    log.info "Starting database migration..."
    log.info "Migrating data from database: ${sourceDbHost}:${sourceDbPort}/${sourceDbName}."
    log.info "Please make sure the database contains no data"

    migrateUsers()
    migrateLunches()
    migrateComments()
    migrateLunchRequests()
  }

  private void migrateUsers() {
    log.info "Migrating users..."

    sql.eachRow("""
    		  select * from twitter_users u
                where u.user in (select user from lunches)
                  or  u.user in (select user from comments)
                  or  u.user in (select twitter_user from lunch_requests)
		    """) {
      def user = new User(
          name: it.fullname,
          username: it.user,
          facebookProfile: it.fb_url,
          linkedInProfile: it.linkedin_url,
          profileImageUrl: "$config.grails.serverURL/img/profile/wannalunch")

      users[it.user] = user
      save(user)
    }

    log.info "Done"
  }

  private void migrateLunches() {
    log.info "Migrating lunches..."

    def createdStartDate = new LocalDateTime(2010, 04, 23, 12, 00)
    def rowId = 0

    sql.eachRow("select * from lunches order by id") {
      if (users[it.user]) {
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

        def description = it.desc.empty ? it.topic : it.desc

        def lunch = new Lunch(
            topic: it.topic,
            description: description,
            createDateTime: createdStartDate.plusMinutes(10 * rowId++),
            date: new LocalDate(it.lunchdate),
            time: new LocalTime(it.lunchtime),
            location: it.place,
            creator: new Luncher(user: users[it.user], wantsNotification: true),
            paymentOption: paymentOption)

        lunches[it.id] = lunch
        save(lunch)
      }
    }

    log.info "Done"
  }

  private void migrateComments() {
    log.info "Migrating comments..."

    sql.eachRow("""
        select
          c.comment comment,
          c.user user,
          c.lunch lunch,
          coalesce(date(c.datetime), l.lunchdate) date,
          coalesce(time(c.datetime), l.lunchtime) time
        from comments c, lunches l
          where l.id = c.lunch""") {
      if (users[it.user] && lunches[it.lunch]) {
        save(new Comment(
            text: it.comment,
            author: users[it.user],
            lunch: lunches[it.lunch],
            date: new LocalDate(it.date),
            time: new LocalTime(it.time))
        )
      }
    }

    log.info "Done"
  }

  private migrateLunchRequests() {
    log.info "Migrating lunch requests..."

    sql.eachRow("select * from lunch_requests") {
      def twitterUser = users[it.twitter_user]
      if (twitterUser) {
        def user = new Luncher(user: twitterUser, wantsNotification: true)
        def lunch = lunches[it.lunch_id]
        if (user && lunch) {
          if (it.selected_for_the_lunch) {
            lunch.addToParticipants(user)
          } else {
            lunch.addToApplicants(user)
          }
  
          save(lunch)
        }
      }
    }
  }

  private void save(entity) {
    assert entity.save(flush: true), entity.errors
  }

}
