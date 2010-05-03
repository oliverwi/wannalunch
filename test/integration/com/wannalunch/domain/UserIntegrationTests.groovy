package com.wannalunch.domain

import com.wannalunch.test.TestDataCreator;

import grails.test.GrailsUnitTestCase;

class UserIntegrationTests extends GrailsUnitTestCase {

  Lunch lunch
  
  User creator
  
  User user
  
  void setUp() {
    user = TestDataCreator.createUser()
    creator = TestDataCreator.createUser()
    lunch = TestDataCreator.createLunch([creator: creator])
    
    user.save()
    creator.save()
    lunch.save()
  }
  
  void tearDown() {
    lunch.delete()
    user.delete()
  }
  
  void testUserCreatesLunch() {
    assertEquals creator, lunch.creator
  }
  
  void testApplyTo() {
    user.applyTo lunch

    assertEquals user, lunch.applicants.iterator().next()
    assertEmpty lunch.participants
  }
  
  void testCancelParticipation() {
    user.applyTo lunch
    user.cancelParticipation lunch
    
    assertEmpty lunch.applicants
    assertEmpty lunch.participants
  }
  
  void testPromoteToParticipant() {
    user.applyTo lunch
    creator.promoteToParticipant user, lunch
    
    assertEquals user, lunch.participants.iterator().next()
    assertEmpty lunch.applicants
  }
  
  private void assertEmpty(collection) {
    assertTrue (collection == null || collection.empty)
  }
}
