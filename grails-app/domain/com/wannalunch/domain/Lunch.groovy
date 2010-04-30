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
  
  String toString() {
    StringBuffer buffer = new StringBuffer()
    buffer << "Lunch [id: $id, "
    buffer << "topic: $topic, "
    buffer << "creator: $creator.username, "
    buffer << "date: $date, "
    buffer << "time: $time]"
    
    return buffer.toString()
  }
  
  def getShortDescription() {
    description.length() <= 100 ? description : description[0..97] + "..."
  }

  def getSortedComments() {
    comments.sort(new CommentComparator())
  }
  
  def getNextUpcomingLunch() {
    def today = new LocalDate()
    def nextLunch = find("from Lunch l where l.id > :id and l.date >= :today order by id", [id: this.id, today: today])
    if (!nextLunch) {
      nextLunch = find("from Lunch l where l.date >= :today order by id", [today: new LocalDate()])
    }
    
    return nextLunch
  }
  
  def getPreviousUpcomingLunch() {
    def today = new LocalDate()
    def previousLunch = find("from Lunch l where l.id < :id and l.date >= :today order by id desc", [id: this.id, today: today])
    if (!previousLunch) {
      previousLunch = find("from Lunch l where l.date >= :today order by id desc", [today: new LocalDate()])
    }
    
    return previousLunch
  }
  
  static def findUpcomingLunches(paginateParams) {
    executeQuery("select l from Lunch l where l.date >= :today order by date, time",
        [today: new LocalDate(), max: paginateParams.max, offset: paginateParams.offset])
  }
  
  static def findFreshlyAddedLunches(paginateParams) {
    executeQuery("select l from Lunch l where l.date >= :today order by l.createDateTime desc",
        [today: new LocalDate(), max: paginateParams.max, offset: paginateParams.offset])
  }
  
  static def countUpcomingLunches() {
    executeQuery("select count(l.id) from Lunch l where l.date >= :today",
        [today: new LocalDate()])[0]
  }

  static def findUpcomingLunchesFor(User user) {
    executeQuery(
        "select l from Lunch l left outer join l.participants p where (l.creator = :user or p = :user) and l.date >= :today order by date, time", 
        [user: user, today: new LocalDate()])
  }

  enum PaymentOption {
    WE_SPLIT("We split"), I_PAY("I pay"), YOU_PAY("You pay");

    String text

    PaymentOption(String text) {
      this.text = text
    }
  }
}
