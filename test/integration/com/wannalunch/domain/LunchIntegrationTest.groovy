package com.wannalunch.domain

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.wannalunch.test.TestDataCreator;

import grails.test.GrailsUnitTestCase;

class LunchIntegrationTest extends GrailsUnitTestCase {

  private def creator
  
  private def lunches = []
  
  void setUp() {
    creator = TestDataCreator.createUser()
    assert creator.save()
    
    def today = new LocalDate()
    
    lunches << TestDataCreator.createLunch([creator: creator, date: today, time: new LocalTime(12, 0)])
    lunches << TestDataCreator.createLunch([creator: creator, date: today, time: new LocalTime(13, 0)])
    lunches << TestDataCreator.createLunch([creator: creator, date: today, time: new LocalTime(13, 0)])
    lunches << TestDataCreator.createLunch([creator: creator, date: today, time: new LocalTime(13, 0)])
    lunches << TestDataCreator.createLunch([creator: creator, date: today.plusDays(1), time: new LocalTime(11, 0)])
    lunches << TestDataCreator.createLunch([creator: creator, date: today.plusDays(1), time: new LocalTime(11, 30)])
    lunches << TestDataCreator.createLunch([creator: creator, date: today.plusDays(5), time: new LocalTime(10, 30)])
    
    lunches.each {
      assert it.save()
      println it
    }
  }
  
  void tearDown() {
    lunches.each{ it.delete() }
    creator.delete()
  }
  
  void testGetNextUpcomingLunch() {
    assertEquals(lunches[1].id, lunches[0].nextUpcomingLunch.id)
    assertEquals(lunches[2].id, lunches[1].nextUpcomingLunch.id)
    assertEquals(lunches[3].id, lunches[2].nextUpcomingLunch.id)
    assertEquals(lunches[4].id, lunches[3].nextUpcomingLunch.id)
    assertEquals(lunches[5].id, lunches[4].nextUpcomingLunch.id)
    assertEquals(lunches[6].id, lunches[5].nextUpcomingLunch.id)
    assertEquals(lunches[0].id, lunches[6].nextUpcomingLunch.id)
  }
  
  void testGetPreviousUpcomingLunch() {
    assertEquals(lunches[0].id, lunches[1].previousUpcomingLunch.id)
    assertEquals(lunches[1].id, lunches[2].previousUpcomingLunch.id)
    assertEquals(lunches[2].id, lunches[3].previousUpcomingLunch.id)
    assertEquals(lunches[3].id, lunches[4].previousUpcomingLunch.id)
    assertEquals(lunches[4].id, lunches[5].previousUpcomingLunch.id)
    assertEquals(lunches[5].id, lunches[6].previousUpcomingLunch.id)
    assertEquals(lunches[6].id, lunches[0].previousUpcomingLunch.id)
  }
}
