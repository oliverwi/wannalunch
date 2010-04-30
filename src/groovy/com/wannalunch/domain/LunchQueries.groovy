package com.wannalunch.domain

import org.joda.time.LocalDate;

class LunchQueries {
  
  def findNextUpcomingLunch() {
    def today = new LocalDate()
    def nextLunch = find("from Lunch l where l.id > :id and l.date >= :today order by id", [id: id, today: today])
    if (!nextLunch) {
      nextLunch = find("from Lunch l where l.date >= :today order by id", [today: new LocalDate()])
    }
    
    return nextLunch
  }
  
  def findPreviousUpcomingLunch() {
    def today = new LocalDate()
    def previousLunch = delegate.find("from Lunch l where l.id < :id and l.date >= :today order by id desc", [id: delegate.id, today: today])
    if (!previousLunch) {
      previousLunch = delegate.find("from Lunch l where l.date >= :today order by id desc", [today: new LocalDate()])
    }
    
    return previousLunch
  }
  
  static def findUpcomingLunches = { paginateParams ->
    delegate.executeQuery("select l from Lunch l where l.date >= :today order by date, time",
        [today: new LocalDate(), max: paginateParams.max, offset: paginateParams.offset])
  }
  
  static def findFreshlyAddedLunches = { paginateParams ->
    delegate.executeQuery("select l from Lunch l where l.date >= :today order by l.createDateTime desc",
        [today: new LocalDate(), max: paginateParams.max, offset: paginateParams.offset])
  }
  
  static def countUpcomingLunches = {
    delegate.executeQuery("select count(l.id) from Lunch l where l.date >= :today",
        [today: new LocalDate()])[0]
  }
  
  static def findUpcomingLunchesFor = { User user ->
    delegate.executeQuery(
        "select l from Lunch l left outer join l.participants p where (l.creator = :user or p = :user) and l.date >= :today order by date, time", 
        [user: user, today: new LocalDate()])
  }
  
  void injectQueries() {
    
    this.class.methods*.name.each { methodName ->
      if (methodName.startsWith("find")) {
        Lunch.metaClass."$methodName" = delegate.&"$methodName"
      }
    }
    
    Lunch.metaClass.static.findUpcomingLunches = findUpcomingLunches
    Lunch.metaClass.static.findFreshlyAddedLunches = findFreshlyAddedLunches
    Lunch.metaClass.static.countUpcomingLunches = countUpcomingLunches
    Lunch.metaClass.static.findUpcomingLunchesFor = findUpcomingLunchesFor
  }
}
