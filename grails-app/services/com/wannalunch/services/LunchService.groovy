package com.wannalunch.services

import com.wannalunch.aop.Tweet;
import com.wannalunch.aop.TweetType;

class LunchService {

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

}
