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

  User creator
  
  boolean creatorWantsNotifications
  
  City city

  PaymentOption paymentOption = PaymentOption.WE_SPLIT
  
  @Delegate LunchQueries lunchQueries = new LunchQueries(this)

  static hasMany = [participants: User, applicants: User, comments: Comment]
  
  static transients = ["lunchQueries"]

  static mapping = {
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
  
  def getSortedComments() {
    comments.sort(new CommentComparator())
  }
  
  def toJsonArray() {
    return toJsonArray(false, false)
  }
  
  def toJsonArray(boolean includeWannas, boolean includeLunchers) {
    def jsonArray = [topic: topic, 
     desc: description, 
     place: location,
     city: city.name,
     date: "$date.dayOfMonth/$date.monthOfYear/$date.year",
     time: "$time.hourOfDay:$time.minuteOfHour",
     whoPays: paymentOption.code,
     creator: creator.toJsonArray()]
    
    if (includeWannas) {
      jsonArray.wannas = applicants.toJsonArray()
    }
    
    if (includeLunchers) {
      jsonArray.lunchers = participants.toJsonArray()
    }
    
    return jsonArray
  }
  
  enum PaymentOption {
    WE_SPLIT("We split", "SPLIT"), I_PAY("I pay", "I"), YOU_PAY("You pay", "YOU");

    String text
    
    String code

    PaymentOption(String text, String code) {
      this.text = text
      this.code = code
    }
    
    static Lunch.PaymentOption fromCode(String code) {
      switch (code.toUpperCase()) {
        case "I":
          return I_PAY
          
        case "YOU":
          return YOU_PAY
          
        default:
          return WE_SPLIT
      }
    }
  }
}
