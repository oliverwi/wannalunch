package com.wannalunch.services

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

import com.wannalunch.aop.Mail;
import com.wannalunch.aop.Mail.Kind;
import com.wannalunch.aop.Tweet;
import com.wannalunch.domain.Comment;
import com.wannalunch.domain.Lunch
import com.wannalunch.domain.Luncher;

class LunchService {

  def userMessageSource

  @Tweet(Tweet.Kind.LUNCH_WITH_ME)
  boolean createLunch(creator, lunch) {
    creator.create(lunch)
  }

  @Tweet(Tweet.Kind.LUNCH_WITH_YOU)
  boolean applyTo(user, lunch) {
    user.applyTo(lunch)
  }

  @Tweet(Tweet.Kind.LUNCH_WITH_EACH_OTHER)
  @Mail(Mail.Kind.ACCEPT)
  boolean promoteToParticipant(applicant, lunch) {
    lunch.creator.promoteToParticipant(applicant, lunch)
  }

  @Mail(Kind.COMMENT)
  def comment(lunch, author, text) {
    def comment = new Comment()
    comment.text = text
    comment.date = new LocalDate()
    comment.time = new LocalTime()
    comment.author = author
    comment.lunch = lunch
    comment.save()
  }

  void remindOfTodaysLunches() {
    log.debug "Maybe sending mail..."

    Lunch.findTodaysLunches().each { lunch ->
      remindOfLunch(lunch)
    }
  }

  private void remindOfLunch(Lunch lunch) {
    def formatter = DateTimeFormat.forPattern(userMessageSource.getMessage("default.time.format"))
    def participants = lunch.participants + lunch.creator

    participants.each { Luncher luncher ->
      def time = formatter.print(lunch.time)
      mailService.sendMail(luncher, Kind.REMINDER, lunch.location, time, lunch.showUrl)
    }
  }

}
