package com.wannalunch.services

import com.wannalunch.aop.Mail;
import com.wannalunch.aop.MailType;
import com.wannalunch.aop.Tweet;
import com.wannalunch.aop.TweetType;

class LunchService {

  @Tweet(TweetType.LUNCH_WITH_ME)
  boolean createLunch(creator, lunch) {
    creator.create(lunch)
  }

  @Tweet(TweetType.LUNCH_WITH_YOU)
  boolean applyTo(user, lunch) {
    user.applyTo(lunch)
  }

  @Tweet(TweetType.LUNCH_WITH_EACH_OTHER)
  @Mail(MailType.ACCEPTED_PARTICIPANT)
  boolean promoteToParticipant(applicant, lunch) {
    lunch.creator.promoteToParticipant(applicant, lunch)
  }

}
