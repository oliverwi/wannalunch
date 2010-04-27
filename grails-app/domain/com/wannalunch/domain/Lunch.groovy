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
  
  static hasMany = [participants:User, comments:Comment]

  def getSortedComments() {
    comments.sort(new CommentComparator())
  }
  
  static def findByParticipant(User user) {
    executeQuery("select l from Lunch l left outer join l.participants p where l.creator = :user or p = :user", [user: user])
  }
}
