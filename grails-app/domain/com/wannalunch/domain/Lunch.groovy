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
}
