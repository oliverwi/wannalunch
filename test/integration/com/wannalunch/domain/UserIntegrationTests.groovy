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
    lunch.city.save()
    lunch.save()
  }
  
  void tearDown() {
    lunch.delete()
    lunch.city.delete()
    user.delete()
    creator.delete()
  }
  
  void testUserCreatesLunch() {
    assertEquals creator, lunch.creator
    assertTrue creator.isCreatorOf(lunch)
  }
  
  void testApplyTo() {
    assertTrue user.applyTo(lunch)

    assertEquals user, lunch.applicants.iterator().next()
    assertTrue user.isApplicantOf(lunch)
    assertEmpty lunch.participants
  }
  
  void testCancelApplication() {
    assertTrue user.applyTo(lunch)
    assertTrue user.cancelParticipation(lunch)
    
    assertFalse user.isApplicantOf(lunch)
    assertEmpty lunch.applicants
    assertEmpty lunch.participants
  }
  
  void testPromoteToParticipant() {
    assertTrue user.applyTo(lunch)
    assertTrue lunch.applicants.contains(user)
    assertTrue creator.promoteToParticipant(user, lunch)
    
    assertTrue user.isParticipantOf(lunch)
    assertEquals user, lunch.participants.iterator().next()
    assertEmpty lunch.applicants
  }
  
  void testCancelParticipation() {
    assertTrue user.applyTo(lunch)
    assertTrue creator.promoteToParticipant(user, lunch)
    assertTrue user.cancelParticipation(lunch)
    
    assertFalse user.isApplicantOf(lunch)
    assertFalse user.isParticipantOf(lunch)
    assertEmpty lunch.participants
    assertEmpty lunch.applicants
  }
  
  void testFindUpcomingLunches() {
    def lunches = user.findUpcomingLunches()
    
    assertEquals 0, lunches.size()
    
    user.applyTo(lunch)
    creator.promoteToParticipant(user, lunch)
    lunches = user.findUpcomingLunches()
    
    assertEquals 1, lunches.size()
    assertEquals lunch, lunches.get(0)
  }
  
  private void assertEmpty(collection) {
    assertTrue (collection == null || collection.empty)
  }
}
