package com.wannalunch.aop

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.wannalunch.domain.Lunch;
import com.wannalunch.domain.User;

@Aspect
class TweetingAspect implements ApplicationContextAware {

  ApplicationContext applicationContext

  @Pointcut("@annotation(com.wannalunch.aop.Tweet)")
  void lunchOperation() {}

  @AfterReturning(pointcut = "com.wannalunch.aop.TweetingAspect.lunchOperation() && @annotation(tweet) && args(lunch)")
  void tweet(Tweet tweet, Lunch lunch) {
    twitterService.tweet(tweet.value(), null, lunch)
  }

  @AfterReturning(pointcut = "com.wannalunch.aop.TweetingAspect.lunchOperation() && @annotation(tweet) && args(user, lunch)")
  void tweet(Tweet tweet, User user, Lunch lunch) {
    twitterService.tweet(tweet.value(), user, lunch)
  }

  private def getTwitterService() {
    applicationContext.twitterService
  }

}
