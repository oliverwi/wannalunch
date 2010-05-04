package com.wannalunch.domain

import org.joda.time.LocalDate;

class LunchQueries {

  def getNextUpcomingLunch = {
    def nextLunch = delegate.find(
        "from Lunch l where " +
          "(l.date > :date) or " +
          "(l.date = :date and l.time > :time) or " +
          "(l.date = :date and l.time = :time and l.id > :id) " +
        "order by date, time, id",
    		[date: delegate.date, time: delegate.time, id: delegate.id])
    
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
        "from Lunch l where l.date >= :today and (" +
          "(l.date = :date and l.time = :time and l.id < :id) or " +
          "(l.date = :date and l.time < :time) or " +
          "(l.date < :date)" +
        ") order by date desc, time desc, id desc",
        [today: today, date: delegate.date, time: delegate.time, id: delegate.id])
    
    if (!previousLunch) {
      previousLunch = delegate.find(
          "from Lunch l where l.date >= :today order by date desc, time desc, id desc",
          [today: today])
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
        "select l from Lunch l left outer join l.participants p where (l.creator.user = :user or p.user = :user) and l.date >= :today order by date, time",
        [user: user, today: new LocalDate()])
  }

  static def findTodaysLunches = {
    delegate.findAllWhere(date: new LocalDate())
  }

  void injectQueries() {
    Lunch.metaClass.getNextUpcomingLunch = getNextUpcomingLunch
    Lunch.metaClass.getPreviousUpcomingLunch = getPreviousUpcomingLunch

    Lunch.metaClass.static.findUpcomingLunches = findUpcomingLunches
    Lunch.metaClass.static.findFreshlyAddedLunches = findFreshlyAddedLunches
    Lunch.metaClass.static.countUpcomingLunches = countUpcomingLunches
    Lunch.metaClass.static.findUpcomingLunchesFor = findUpcomingLunchesFor
    Lunch.metaClass.static.findTodaysLunches = findTodaysLunches
  }
}
