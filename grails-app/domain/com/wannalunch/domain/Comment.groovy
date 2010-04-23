package com.wannalunch.domain

import org.joda.time.LocalDate
import org.joda.time.LocalTime

class Comment {

  String text
  
  User author
  
  LocalDate date
  
  LocalTime time
  
  static belongsTo = [lunch:Lunch]
}
