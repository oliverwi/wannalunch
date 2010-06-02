package com.wannalunch.controllers;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.wannalunch.domain.City;
import com.wannalunch.test.TestDataCreator;

import grails.converters.JSON;
import grails.test.GrailsUnitTestCase;

class LunchControllerIntegrationTest extends GrailsUnitTestCase {
  
  private def today
  
  private def cities
  
  private def creator

  private def lunches
  
  private def controller
  
  void setUp() {
    cities = []
    cities << TestDataCreator.createCity()
    cities << TestDataCreator.createCity()
    cities.each {
      assert it.save(), it.errors
    }
    
    creator = TestDataCreator.createUser()
    assert creator.save()
    
    def wanna1 = TestDataCreator.createUser()
    assert wanna1.save()
    
    def luncher1 = TestDataCreator.createUser()
    assert luncher1.save()
    
    today = new LocalDate()
    
    lunches = []
    lunches << TestDataCreator.createLunch([creator: creator, date: today, time: new LocalTime(12, 14), city: cities[0]])
    lunches << TestDataCreator.createLunch([creator: creator, date: today, time: new LocalTime(12, 14), city: cities[1]])
    lunches.each { lunch ->
      assert lunch.save()
    }
    
    lunches[0].addToApplicants wanna1
    lunches[0].addToParticipants luncher1
    assert lunches[0].save()
    
    controller = new LunchController()
  }
  
  void testInfo() {
    def lunch = lunches[0]
    controller.params.id = lunch.id
    controller.info()

    def jsonArray = JSON.parse(controller.response.contentAsString)
    
    assertLunchInfo lunch, jsonArray
    assertLunchCreatorInfo lunch, jsonArray
    assertNull jsonArray.wannas
    assertNull jsonArray.lunchers
  }
  
  void testInfoIncludeWannas() {
    def lunch = lunches[0]
    controller.params.id = lunch.id
    controller.params.wannas = "true"
    controller.info()

    def jsonArray = JSON.parse(controller.response.contentAsString)
    
    assertLunchInfo lunch, jsonArray
    assertLunchCreatorInfo lunch, jsonArray
    assertLunchWannasInfo lunch, jsonArray
    assertNull jsonArray.lunchers
  }
  
  void testInfoIncludeLunchers() {
    def lunch = lunches[0]
    controller.params.id = lunch.id
    controller.params.lunchers = "true"
    controller.info()

    def jsonArray = JSON.parse(controller.response.contentAsString)
    
    assertLunchInfo lunch, jsonArray
    assertLunchCreatorInfo lunch, jsonArray
    assertLunchLunchersInfo lunch, jsonArray
    assertNull jsonArray.wannas
  }
  
  void testFindLunchesByUser() {
    controller.params.user = creator.username
    controller.query()
    
    def jsonArray = JSON.parse(controller.response.contentAsString)
    
    assertEquals 2, jsonArray.size()
    assertEquals lunches[0].topic, jsonArray[0]["topic"]
    assertEquals lunches[1].topic, jsonArray[1]["topic"]
  }
  
  void testFindLunchesByCityWhenUsingCity0() {
    controller.params.city = cities[0].name
    controller.query()
    
    def jsonArray = JSON.parse(controller.response.contentAsString)
    
    assertEquals 1, jsonArray.size()
    assertEquals lunches[0].topic, jsonArray[0]["topic"]
  }
  
  void testFindLunchesByCityWhenUsingCity1() {
    controller.params.city = cities[1].name
    controller.query()
    
    def jsonArray = JSON.parse(controller.response.contentAsString)
    
    assertEquals 1, jsonArray.size()
    assertEquals lunches[1].topic, jsonArray[0]["topic"]
  }
  
  private void assertLunchInfo(def lunch, def jsonArray) {
    assertEquals lunch.topic, jsonArray.topic
    assertEquals cities[0].name, jsonArray.city
    assertEquals lunch.description, jsonArray.desc
    
    def expectedDate = today.dayOfMonth + "/" + today.monthOfYear + "/" + today.year
    def expectedTime = "12:14"
    
    assertEquals expectedDate, jsonArray.date
    assertEquals expectedTime, jsonArray.time
  }
  
  private void assertLunchCreatorInfo(def lunch, def jsonArray) {
    assertEquals creator.username, jsonArray.creator["username"]
    assertEquals creator.name, jsonArray.creator["name"]
    assertEquals creator.profileImageUrl, jsonArray.creator["img"]
  }
  
  private void assertLunchWannasInfo(def lunch, def jsonArray) {
    def wanna = lunch.applicants.iterator().next()
    assertEquals wanna.username, jsonArray.wannas[0]["username"]
    assertEquals wanna.name, jsonArray.wannas[0]["name"]
    assertEquals wanna.profileImageUrl, jsonArray.wannas[0]["img"]    
  }
  
  private void assertLunchLunchersInfo(def lunch, def jsonArray) {
    def luncher = lunch.participants.iterator().next()
    assertEquals luncher.username, jsonArray.lunchers[0]["username"]
    assertEquals luncher.name, jsonArray.lunchers[0]["name"]
    assertEquals luncher.profileImageUrl, jsonArray.lunchers[0]["img"]    
  }
}
