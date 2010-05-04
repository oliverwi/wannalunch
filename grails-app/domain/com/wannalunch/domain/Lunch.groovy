package com.wannalunch.domain

import org.codehaus.groovy.grails.commons.ConfigurationHolder;
import org.joda.time.LocalDateTime
import org.joda.time.LocalDate
import org.joda.time.LocalTime

import com.wannalunch.util.CommentComparator;

class Lunch {

  String topic

  String description

  LocalDateTime createDateTime

  LocalDate date

  LocalTime time

  String location

  Luncher creator

  PaymentOption paymentOption = PaymentOption.WE_SPLIT

  static hasMany = [participants: Luncher, applicants: Luncher, comments: Comment]

  static mapping = {
    creator cascade:'all, delete-orphan'
    applicants cascade:'all, delete-orphan'
    participants cascade:'all, delete-orphan'
  }

  static constraints = {
    topic nullable: false, blank: false
    description nullable: false, blank: false
    location nullable: false, blank: false
  }

  static transients = ['showUrl']

  def getShortDescription() {
    description.length() <= 100 ? description : description[0..97] + "..."
  }

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
