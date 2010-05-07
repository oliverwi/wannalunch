package com.wannalunch.domain

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.wannalunch.test.TestDataCreator;

import grails.test.GrailsUnitTestCase;

class LunchIntegrationTest extends GrailsUnitTestCase {

  private def creator
  
  private def cities = []
  
  private def lunches = []
  
  void setUp() {
    creator = TestDataCreator.createUser()
    assert creator.save()
    
    def today = new LocalDate()
    
    cities << new City(name: "Tallinn")
    cities << new City(name: "Tartu")
    
    lunches << TestDataCreator.createLunch([creator: creator, date: today.minusDays(1), time: new LocalTime(12, 0), city: cities[0]])
    lunches << TestDataCreator.createLunch([creator: creator, date: today, time: new LocalTime(12, 0), city: cities[0]])
    lunches << TestDataCreator.createLunch([creator: creator, date: today, time: new LocalTime(13, 0), city: cities[0]])
    lunches << TestDataCreator.createLunch([creator: creator, date: today, time: new LocalTime(13, 0), city: cities[0]])
    lunches << TestDataCreator.createLunch([creator: creator, date: today, time: new LocalTime(13, 0), city: cities[0]])
    lunches << TestDataCreator.createLunch([creator: creator, date: today.minusDays(1), time: new LocalTime(13, 0), city: cities[0]])
    lunches << TestDataCreator.createLunch([creator: creator, date: today.plusDays(1), time: new LocalTime(11, 30), city: cities[0]])
    lunches << TestDataCreator.createLunch([creator: creator, date: today.plusDays(1), time: new LocalTime(11, 0), city: cities[0]])
    lunches << TestDataCreator.createLunch([creator: creator, date: today.plusDays(5), time: new LocalTime(10, 30), city: cities[0]])
    lunches << TestDataCreator.createLunch([creator: creator, date: today.minusDays(1), time: new LocalTime(19, 0), city: cities[0]])
    lunches << TestDataCreator.createLunch([creator: creator, date: today.plusDays(5), time: new LocalTime(11, 30), city: cities[1]])
    
    cities.each {
      assert it.save(), it.errors
    }
    
    lunches.each {
      assert it.save()
    }
  }
  
  void tearDown() {
    lunches.each{ it.delete() }
    cities.each{ it.delete() }
    creator.delete()
  }
  
  void testGetNextUpcomingLunch() {
    assertEquals(lunches[2].id, lunches[1].nextUpcomingLunch.id)
    assertEquals(lunches[3].id, lunches[2].nextUpcomingLunch.id)
    assertEquals(lunches[4].id, lunches[3].nextUpcomingLunch.id)
    assertEquals(lunches[7].id, lunches[4].nextUpcomingLunch.id)
    assertEquals(lunches[6].id, lunches[7].nextUpcomingLunch.id)
    assertEquals(lunches[8].id, lunches[6].nextUpcomingLunch.id)
    assertEquals(lunches[1].id, lunches[8].nextUpcomingLunch.id)
  }
  
  void testGetPreviousUpcomingLunch() {
    assertEquals(lunches[8].id, lunches[1].previousUpcomingLunch.id)
    assertEquals(lunches[6].id, lunches[8].previousUpcomingLunch.id)
    assertEquals(lunches[7].id, lunches[6].previousUpcomingLunch.id)
    assertEquals(lunches[4].id, lunches[7].previousUpcomingLunch.id)
    assertEquals(lunches[3].id, lunches[4].previousUpcomingLunch.id)
    assertEquals(lunches[2].id, lunches[3].previousUpcomingLunch.id)
    assertEquals(lunches[1].id, lunches[2].previousUpcomingLunch.id)
  }
  
  void testDeleteLunch() {
    def newLunch = TestDataCreator.createLunch([creator: creator, date: new LocalDate(), time: new LocalTime(12, 0), city: cities[0]])
    newLunch.delete(flush: true)
    
    assertNull(Lunch.get(newLunch.id))
    assert User.get(newLunch.creator.id)
  }
}
