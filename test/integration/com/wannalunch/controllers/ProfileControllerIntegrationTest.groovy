package com.wannalunch.controllers

import com.wannalunch.domain.FacebookAccount;
import com.wannalunch.domain.TwitterAccount;
import com.wannalunch.domain.User;
import com.wannalunch.test.TestDataCreator;

import grails.converters.JSON;
import grails.test.GrailsUnitTestCase;

class ProfileControllerIntegrationTest extends GrailsUnitTestCase {

  def user
  
  def controller
  
  void setUp() {
    user = TestDataCreator.createUser()
    
    user.twitterAccount = new TwitterAccount(username: "lele")
    user.facebookAccount = new FacebookAccount(userId: 123)
    
    assert user.save()
    
    controller = new ProfileController()
  }
  
  void testInfo() {
    controller.params.id = user.username
    controller.info()
    
    def jsonArray = JSON.parse(controller.response.contentAsString)
    
    assertEquals user.username, jsonArray.username
    assertEquals user.name, jsonArray.name
    assertEquals user.email, jsonArray.email
    assertEquals user.profileImageUrl, jsonArray.img
    assertEquals user.linkedInProfile, jsonArray.linkedin
    assertEquals "lele", jsonArray.twitter
    assertEquals "http://www.facebook.com/profile.php?id=123", jsonArray.facebook
  }
}
