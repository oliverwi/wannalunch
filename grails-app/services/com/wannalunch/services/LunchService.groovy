package com.wannalunch.services

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

import com.wannalunch.aop.Tweet;
import com.wannalunch.domain.Comment;
import com.wannalunch.domain.Lunch

class LunchService {

  def notificationService
  
  def userMessageSource

  @Tweet(Tweet.Kind.LUNCH_WITH_ME)
  boolean createLunch(creator, lunch) {
    creator.create(lunch)
  }

  @Tweet(Tweet.Kind.LUNCH_WITH_YOU)
  boolean applyTo(user, lunch) {
    if (user.applyTo(lunch)) {
      notificationService.sendApplicationNotification(user, lunch)
      return true
    }
    return false
  }

  @Tweet(Tweet.Kind.LUNCH_WITH_EACH_OTHER)
  boolean promoteToParticipant(applicant, lunch) {
    if (lunch.creator.promoteToParticipant(applicant, lunch)) {
      notificationService.sendAcceptanceNotification(applicant, lunch)
      return true
    }
    return false
  }

  Comment comment(lunch, author, text) {
    Comment comment = author.comment(text, lunch)
    notificationService.sendCommentNotification(comment)

    return comment
  }

  void remindOfTodaysLunches() {
//    log.debug "Maybe sending mail..."
//
//    Lunch.findTodaysLunches().each { lunch ->
//      remindOfLunch(lunch)
//    }
  }

  private void remindOfLunch(Lunch lunch) {
//    def formatter = DateTimeFormat.forPattern(userMessageSource.getMessage("default.time.format"))
//    def participants = lunch.participants + lunch.creator
//
//    participants.each { Luncher luncher ->
//      def time = formatter.print(lunch.time)
//      mailService.sendMail(luncher, Kind.REMINDER, [luncher.name, lunch.location, time, lunch.showUrl])
//    }
  }

}
