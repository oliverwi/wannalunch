package com.wannalunch.domain

import org.joda.time.LocalDate
import org.joda.time.LocalTime
import org.joda.time.contrib.hibernate.PersistentLocalDate;
import org.joda.time.contrib.hibernate.PersistentLocalTimeAsTime;

class Comment {

  String text

  User author

  LocalDate date

  LocalTime time

  static belongsTo = [lunch:Lunch]

  static mapping = {
    text type: 'text'

    date type: PersistentLocalDate
    time type: PersistentLocalTimeAsTime
  }
}
