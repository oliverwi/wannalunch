package com.wannalunch.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder;
import org.joda.time.LocalDateTime
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import org.joda.time.contrib.hibernate.PersistentLocalDate;
import org.joda.time.contrib.hibernate.PersistentLocalDateTime;
import org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime;

import com.wannalunch.util.CommentComparator;

class Lunch {

  String topic

  String description

  LocalDateTime createDateTime

  LocalDate date

  LocalTime time

  String location

  Luncher creator

  City city

  PaymentOption paymentOption = PaymentOption.WE_SPLIT

  static hasMany = [participants: Luncher, applicants: Luncher, comments: Comment]

  static mapping = {
    creator cascade:'all, delete-orphan', column: 'lunch_creator_id'
    applicants cascade:'all, delete-orphan', column: 'lunch_applicant_id'
    participants cascade:'all, delete-orphan', column: 'lunch_participant_id'
    description type: 'text'

    createDateTime type: PersistentLocalDateTime
    date type: PersistentLocalDate
    time type: PersistentLocalTimeAsTime
  }

  static constraints = {
    topic nullable: false, blank: false
    description nullable: false, blank: false
    location nullable: false, blank: false
  }

  static transients = ['showUrl']

  def getSortedComments() {
    comments.sort(new CommentComparator())
  }

  def getShowUrl() {
    "${ConfigurationHolder.config.grails.serverURL}/lunch/show/$id"
  }

  enum PaymentOption {
    WE_SPLIT("We split"), I_PAY("I pay"), YOU_PAY("You pay");

    String text

    PaymentOption(String text) {
      this.text = text
    }
  }
}
