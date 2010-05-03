package com.wannalunch.services

import org.codehaus.groovy.grails.commons.ConfigurationHolder;
import org.joda.time.format.DateTimeFormat;

import com.wannalunch.aop.Tweet;
import com.wannalunch.aop.TweetType;
import com.wannalunch.domain.Lunch
import com.wannalunch.domain.User;

class LunchService {

  def mailService
  def userMessageSource
  def config = ConfigurationHolder.config

  @Tweet(TweetType.LUNCH_WITH_ME)
  boolean createLunch(lunch) {
    lunch.save()
  }

  @Tweet(TweetType.LUNCH_WITH_YOU)
  boolean applyTo(user, lunch) {
    user.applyTo(lunch)
  }

  @Tweet(TweetType.LUNCH_WITH_EACH_OTHER)
  boolean promoteToParticipant(applicant, lunch) {
    lunch.creator.promoteToParticipant(applicant, lunch)
  }

  void notifyOfTodaysLunches() {
    log.debug "Maybe sending mail..."

    Lunch.findTodaysLunches().each { lunch ->
      notifyOfLunch(lunch)
    }
  }

  private void notifyOfLunch(Lunch lunch) {
    def formatter = DateTimeFormat.forPattern(userMessageSource.getMessage("default.time.format"))
    lunch.participants.each { User participant ->
      def time = formatter.print(lunch.time)
      mailService.sendMail(
          to: participant.email,
          subject: "You have an upcoming lunch today",
          text: "Dear $participant.name,\n\n" +
          		"Please, do not forget that you have a lunch today with $lunch.creator.name ($config.grails.serverURL/lunch/show/$lunch.id).\n\n" +
          		"Where: @ $lunch.location\n" +
          		"When: $time\n\n" +
          		"Bon appetit!\n" +
          		"wannalunch.com")
    }
  }

}
