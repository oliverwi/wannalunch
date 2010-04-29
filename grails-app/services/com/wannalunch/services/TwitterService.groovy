package com.wannalunch.services

import org.codehaus.groovy.grails.commons.ConfigurationHolder

import com.wannalunch.aop.TweetType;
import com.wannalunch.domain.Lunch;
import com.wannalunch.domain.User;

import twitter4j.Twitter

class TwitterService {

  static scope = "session"

  boolean transactional = false

  def userService

  def consumerKey = ConfigurationHolder.config.twitter.oauth.consumerKey
  def consumerSecret = ConfigurationHolder.config.twitter.oauth.consumerSecret

  Twitter client = new Twitter()

  def requestToken
  def accessToken
  def twitterUser

  def generateRequestToken(returnUrl) {
    log.debug "Generating request with return url of [${returnUrl}]"
    client.setOAuthConsumer(consumerKey, consumerSecret)
    requestToken = client.getOAuthRequestToken(returnUrl)
  }

  def validate(oauthVerifierParam) {
    accessToken = client.getOAuthAccessToken(requestToken, oauthVerifierParam)
    log.debug "Attempting validate..."
    twitterUser = client.verifyCredentials()
    userService.maybeCreateAccount(twitterUser)
    log.debug "Validate successful for ${twitterUser.screenName}"
  }

  def tweet(TweetType tweetType, User user, Lunch lunch) {
    def status = constructStatus(tweetType, user, lunch)

    if (ConfigurationHolder.config.twitter.sendTweets) {
      client.updateStatus status
    } else {
      log.debug "Not sending tweet: '$status'"
    }
  }

  private def constructLunchDetailsUrl(lunch) {
    ConfigurationHolder.config.grails.serverURL + "/lunch/show/$lunch.id"
  }

  private def constructStatus(tweetType, user, lunch) {
    def lunchDetailsUrl = constructLunchDetailsUrl(lunch)

    switch (tweetType) {
      case TweetType.LUNCH_WITH_YOU:
        return "@${lunch.creator.username} #wannalunch with you $lunchDetailsUrl"
      case TweetType.LUNCH_WITH_ME:
        return "#wannalunch with me? See more information $lunchDetailsUrl"
      case TweetType.LUNCH_WITH_EACH_OTHER:
        return "${user.username} and @${lunch.creator.username} #wannalunch $lunchDetailsUrl"
      default:
        throw new IllegalArgumentException("Unknown tweet type " + tweetType)
    }
  }

}