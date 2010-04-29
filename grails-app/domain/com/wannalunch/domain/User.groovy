package com.wannalunch.domain

import java.io.Serializable

import com.wannalunch.aop.Tweet;
import com.wannalunch.aop.TweetType;

class User implements Serializable {

  String name

  String username

  String profileImageUrl

  String facebookProfile

  String linkedInProfile

  static constraints = {
    username blank: false, unique: true
    facebookProfile nullable: true
    linkedInProfile nullable: true
  }

  boolean applyTo(Lunch lunch) {
    lunch.addToApplicants(this)
    return lunch.save()
  }

  boolean cancelParticipation(Lunch lunch) {
    if (isApplicantOf(lunch)) {
      lunch.removeFromApplicants(this)
      return lunch.save()
    }
    if (isParticipantOf(lunch)) {
      lunch.removeFromParticipants(this)
      return lunch.save()
    }
    return false
  }

  boolean promoteToParticipant(User applicant, Lunch lunch) {
    if (!isCreatorOf(lunch) || !applicant.isApplicantOf(lunch)) {
      return false
    }

    lunch.removeFromApplicants(applicant)
    lunch.addToParticipants(applicant)

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
    lunch.creator == this
  }

  boolean isApplicantOf(Lunch lunch) {
    lunch.applicants.contains(this)
  }

  boolean isParticipantOf(Lunch lunch) {
    lunch.participants.contains(this)
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
