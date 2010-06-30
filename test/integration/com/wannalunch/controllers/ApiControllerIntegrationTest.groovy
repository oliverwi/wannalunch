package com.wannalunch.controllers

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.wannalunch.domain.Lunch;
import com.wannalunch.test.TestDataCreator;
import com.wannalunch.test.TwitterServiceMock;

import grails.test.GrailsUnitTestCase;

class ApiControllerIntegrationTest extends GrailsUnitTestCase {
  
  def controller
  
  def user
  
  def city
  
  def lunchService
  
  def twitterServiceMock
  
  void setUp() {
    user = TestDataCreator.createUser()
    city = TestDataCreator.createCity()
    
    assert user.save(flush: true)
    assert city.save(flush: true)
    
    twitterServiceMock = new TwitterServiceMock(user: user)
    controller = new ApiController(twitterService: twitterServiceMock, lunchService: lunchService)
  }
  
  void testNewLunch() {
    controller.params.authType = "twitter"
    controller.params.cityName = city.name
    controller.params.topic = "Api newLunch topic"
    controller.params.description = "Api newLunch description"
    controller.params.location = "Api newLunch location"
    controller.params.whoPays = "You"
    controller.params.date = "2010-07-30"
    controller.params.time = "13:00"
    controller.newLunch()
    
    Lunch createdLunch = Lunch.findByTopic("Api newLunch topic")
    
    assertEquals Lunch.PaymentOption.YOU_PAY, createdLunch.paymentOption
    assertEquals new LocalDate(2010, 7, 30), createdLunch.date
    assertEquals new LocalTime(13, 0), createdLunch.time
    assertEquals user, createdLunch.creator
  }
}
