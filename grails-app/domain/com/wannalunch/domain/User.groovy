package com.wannalunch.domain

import java.io.Serializable

import org.joda.time.LocalDateTime;

class User implements Serializable {

  String name

  String username

  String email

  String profileImageUrl

  String facebookProfile

  String linkedInProfile

  static constraints = {
    username blank: false, unique: true
    email unique: true, email: true, nullable: true
    facebookProfile nullable: true
    linkedInProfile nullable: true
  }
  
  static mapping = {
    table 'wl_user'
  }
  
  boolean create(Lunch lunch) {
    lunch.createDateTime = new LocalDateTime()
    return lunch.save()
  }

  boolean applyTo(Lunch lunch) {
    lunch.addToApplicants(new Luncher(user: this))
    return lunch.save()
  }

  boolean cancelParticipation(Lunch lunch) {
    if (isApplicantOf(lunch)) {
      lunch.removeFromApplicants(new Luncher(user: this))
      return lunch.save()
    }
    if (isParticipantOf(lunch)) {
      lunch.removeFromParticipants(new Luncher(user: this))
      return lunch.save()
    }
    return false
  }

  boolean promoteToParticipant(User applicant, Lunch lunch) {
    if (!isCreatorOf(lunch) || !applicant.isApplicantOf(lunch)) {
      return false
    }

    Luncher luncherApplicant = lunch.applicants.find { it.user == applicant }
    if (!luncherApplicant) {
      return false
    }
    
    lunch.removeFromApplicants(luncherApplicant)
    lunch.addToParticipants(new Luncher(user: luncherApplicant.user))

    return lunch.save()
  }

  boolean canDelete(Lunch lunch) {
    isCreatorOf(lunch)
  }

  boolean canBeRemovedFrom(Lunch lunch) {
    (isApplicantOf(lunch) || isParticipantOf(lunch)) && !isCreatorOf(lunch)
  }

  boolean canApplyTo(Lunch lunch) {
    !isApplicantOf(lunch) && !isParticipantOf(lunch) && !isCreatorOf(lunch)
  }

  boolean canAcceptApplicantsFor(Lunch lunch) {
    isCreatorOf(lunch)
  }

  boolean isCreatorOf(Lunch lunch) {
    lunch.creator == new Luncher(user: this)
  }

  boolean isApplicantOf(Lunch lunch) {
    lunch.applicants.contains(new Luncher(user: this))
  }

  boolean isParticipantOf(Lunch lunch) {
    lunch.participants.contains(new Luncher(user: this))
  }

  public boolean equals(Object o) {
    if (!(o instanceof User)) {
      return false
    }

    User user = (User) o
    return username.equals(user.username)
  }

  public int hashCode() {
    username.hashCode()
  }
}
