package com.wannalunch.domain

import org.joda.time.LocalDate;

class LunchQueries {

  def getNextUpcomingLunch = {
    def nextLunch = delegate.find(
        "from Lunch l where city = :city and (" +
          "(l.date > :date) or " +
          "(l.date = :date and l.time > :time) or " +
          "(l.date = :date and l.time = :time and l.id > :id) " +
        ") order by date, time, id",
    		[city: delegate.city, date: delegate.date, time: delegate.time, id: delegate.id])
    
    if (!nextLunch) {
      nextLunch = delegate.find(
          "from Lunch l where l.date >= :today order by date, time, id",
          [today: new LocalDate()])
    }
    
    return nextLunch
  }

  def getPreviousUpcomingLunch = {
    def today = new LocalDate()
    
    def previousLunch = delegate.find(
        "from Lunch l where l.date >= :today and city = :city and (" +
          "(l.date = :date and l.time = :time and l.id < :id) or " +
          "(l.date = :date and l.time < :time) or " +
          "(l.date < :date)" +
        ") order by date desc, time desc, id desc",
        [today: today, city: delegate.city, date: delegate.date, time: delegate.time, id: delegate.id])
    
    if (!previousLunch) {
      previousLunch = delegate.find(
          "from Lunch l where l.date >= :today order by date desc, time desc, id desc",
          [today: today])
    }

    return previousLunch
  }

  static def findUpcomingLunchesInCity = { City city, paginateParams ->
    delegate.executeQuery("select l from Lunch l where city = :city and l.date >= :today order by date, time",
        [city: city, today: new LocalDate(), max: paginateParams.max, offset: paginateParams.offset])
  }

  static def findFreshlyAddedLunchesInCity = { City city, paginateParams ->
    delegate.executeQuery("select l from Lunch l where city = :city and l.date >= :today order by l.createDateTime desc",
        [city: city, today: new LocalDate(), max: paginateParams.max, offset: paginateParams.offset])
  }

  static def countUpcomingLunchesInCity = { City city ->
    delegate.executeQuery("select count(l.id) from Lunch l where city = :city and l.date >= :today",
        [city: city, today: new LocalDate()])[0]
  }

  static def findUpcomingLunchesFor = { User user ->
    delegate.executeQuery(
        "select l from Lunch l left outer join l.participants p where (l.creator.user = :user or p.user = :user) and l.date >= :today order by date, time",
        [user: user, today: new LocalDate()])
  }

  static def findTodaysLunches = {
    delegate.findAllWhere(date: new LocalDate())
  }

  void injectQueries() {
    Lunch.metaClass.getNextUpcomingLunch = getNextUpcomingLunch
    Lunch.metaClass.getPreviousUpcomingLunch = getPreviousUpcomingLunch

    Lunch.metaClass.static.findUpcomingLunchesInCity = findUpcomingLunchesInCity
    Lunch.metaClass.static.findFreshlyAddedLunchesInCity = findFreshlyAddedLunchesInCity
    Lunch.metaClass.static.countUpcomingLunchesInCity = countUpcomingLunchesInCity
    Lunch.metaClass.static.findUpcomingLunchesFor = findUpcomingLunchesFor
    Lunch.metaClass.static.findTodaysLunches = findTodaysLunches
  }
}
