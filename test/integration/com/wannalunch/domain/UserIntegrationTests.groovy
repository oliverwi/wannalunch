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
    creator.delete()
  }
  
  void testUserCreatesLunch() {
    assertEquals new Luncher(user: creator), lunch.creator
    assertTrue creator.isCreatorOf(lunch)
  }
  
  void testApplyTo() {
    assertTrue user.applyTo(lunch)

    assertEquals new Luncher(user: user), lunch.applicants.iterator().next()
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
    assertTrue lunch.applicants.contains(new Luncher(user: user))
    assertTrue creator.promoteToParticipant(user, lunch)
    
    assertTrue user.isParticipantOf(lunch)
    assertEquals new Luncher(user: user), lunch.participants.iterator().next()
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
  
  private void assertEmpty(collection) {
    assertTrue (collection == null || collection.empty)
  }
}
