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

  static hasMany = [participants:User, applicants: User, comments:Comment]

  static constraints = {
    topic nullable: false, blank: false
    description nullable: false, blank: false
    location nullable: false, blank: false
  }

  def getSortedComments() {
    comments.sort(new CommentComparator())
  }

  static def findUpcomingLunchesFor(User user) {
    executeQuery(
        "select l from Lunch l left outer join l.participants p where (l.creator = :user or p = :user) and l.date >= :today order by date, time", 
        [user: user, today: new LocalDate()])
  }
}
