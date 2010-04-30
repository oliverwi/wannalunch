package com.wannalunch.domain

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

  User creator

  PaymentOption paymentOption = PaymentOption.WE_SPLIT

  static hasMany = [participants:User, applicants: User, comments:Comment]

  static constraints = {
    topic nullable: false, blank: false
    description nullable: false, blank: false
    location nullable: false, blank: false
  }
  
  def getShortDescription() {
    description.length() <= 100 ? description : description[0..97] + "..."
  }

  def getSortedComments() {
    comments.sort(new CommentComparator())
  }

  enum PaymentOption {
    WE_SPLIT("We split"), I_PAY("I pay"), YOU_PAY("You pay");

    String text

    PaymentOption(String text) {
      this.text = text
    }
  }
}
