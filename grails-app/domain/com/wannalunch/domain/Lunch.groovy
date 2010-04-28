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

  def getSortedComments() {
    comments.sort(new CommentComparator())
  }

  static def findWhereCreatorOrParticipant(User user) {
    executeQuery("select l from Lunch l left outer join l.participants p where l.creator = :user or p = :user order by date, time", [user: user])
  }

  enum PaymentOption {
    WE_SPLIT("We split"), I_PAY("I pay"), YOU_PAY("You pay");

    String text

    PaymentOption(String text) {
      this.text = text
    }
  }
}
