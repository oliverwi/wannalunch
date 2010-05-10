package com.wannalunch.domain

import org.joda.time.LocalDate;

class LunchQueries {
  
  private Lunch lunch
  
  static {
    LunchQueries.metaClass.methods.each {
      if (it.static) {
        Lunch.metaClass.static."$it.name" = owner.&"$it.name"
      }
    }
  }
  
  public LunchQueries(Lunch lunch) {
    this.lunch = lunch
  }

  def getNextUpcomingLunch() {
    def nextLunch = Lunch.find(
        "from Lunch l where l.city = :city and (" +
          "(l.date > :date) or " +
          "(l.date = :date and l.time > :time) or " +
          "(l.date = :date and l.time = :time and l.id > :id) " +
        ") order by date, time, id",
    		[city: lunch.city, date: lunch.date, time: lunch.time, id: lunch.id])
    
    if (!nextLunch) {
      nextLunch = Lunch.find(
          "from Lunch l where l.city = :city and l.date >= :today order by date, time, id",
          [city: lunch.city, today: new LocalDate()])
    }
    
    return nextLunch
  }

  def getPreviousUpcomingLunch() {
    def today = new LocalDate()
    
    def previousLunch = Lunch.find(
        "from Lunch l where l.date >= :today and city = :city and (" +
          "(l.date = :date and l.time = :time and l.id < :id) or " +
          "(l.date = :date and l.time < :time) or " +
          "(l.date < :date)" +
        ") order by date desc, time desc, id desc",
        [today: today, city: lunch.city, date: lunch.date, time: lunch.time, id: lunch.id])
    
    if (!previousLunch) {
      previousLunch = Lunch.find(
          "from Lunch l where l.city = :city and l.date >= :today order by date desc, time desc, id desc",
          [city: lunch.city, today: today])
    }

    return previousLunch
  }

  static def findUpcomingLunchesInCity(City city, paginateParams) {
    Lunch.executeQuery("select l from Lunch l where city = :city and l.date >= :today order by date, time",
        [city: city, today: new LocalDate(), max: paginateParams.max, offset: paginateParams.offset])
  }

  static def findFreshlyAddedLunchesInCity(City city, paginateParams) {
    Lunch.executeQuery("select l from Lunch l where city = :city and l.date >= :today order by l.createDateTime desc",
        [city: city, today: new LocalDate(), max: paginateParams.max, offset: paginateParams.offset])
  }

  static def countUpcomingLunchesInCity(City city) {
    Lunch.executeQuery("select count(l.id) from Lunch l where city = :city and l.date >= :today",
        [city: city, today: new LocalDate()])[0]
  }

  static def findTodaysLunches() {
    Lunch.findAllWhere(date: new LocalDate())
  }
}
