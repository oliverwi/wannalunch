package com.wannalunch.domain

import java.io.Serializable

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

class User implements Serializable {

  String name

  String username

  String email

  String profileImageUrl

  String linkedInProfile

  TwitterAccount twitterAccount

  FacebookAccount facebookAccount

  static constraints = {
    username blank: false, unique: true
    email email: true, nullable: true
    linkedInProfile nullable: true
    twitterAccount nullable: true
    facebookAccount nullable: true
  }

  static mapping = {
    table 'wl_user'
  }

  boolean create(Lunch lunch) {
    lunch.creator = this
    return lunch.save()
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

  Comment comment(String commentText, Lunch lunch) {
    Comment comment = new Comment()
    comment.text = commentText
    comment.date = new LocalDate()
    comment.time = new LocalTime()
    comment.author = this
    comment.lunch = lunch

    comment.save()
    return comment
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

  def findUpcomingLunches() {
    executeQuery(
      "select l from Lunch l left outer join l.participants p where (" +
        "l.creator = :user or p = :user) and l.date >= :today order by date, time",
      [user: this, today: new LocalDate()])
  }

//  def getFacebookProfile() {
//    facebookAccount ? facebookAccount.profileUrl : ''
//  }
//
//  def setFacebookProfile(profileUrl) {
//    if (!facebookAccount) {
//      facebookAccount = new FacebookAccount()
//    }
//
//    facebookAccount.profileUrl = profileUrl
//  }

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
