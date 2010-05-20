package com.wannalunch.services

import java.io.Serializable;

import org.codehaus.groovy.grails.commons.ConfigurationHolder

import com.wannalunch.aop.Tweet;
import com.wannalunch.aop.Tweet.Kind;
import com.wannalunch.domain.Lunch;
import com.wannalunch.domain.User;

import twitter4j.Twitter

class TwitterService implements Serializable {

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

  def validate(oauthVerifierParam, merge) {
    accessToken = client.getOAuthAccessToken(requestToken, oauthVerifierParam)
    log.debug "Attempting validate..."
    twitterUser = client.verifyCredentials()
    log.debug "Validate successful for ${twitterUser.screenName}"
    userService.maybeCreateTwitterAccount(twitterUser, merge)
  }

  def tweet(Kind kind, User user, Lunch lunch) {
    if (!user.twitterAccount) {
      log.debug "User $user.username doesn't have twitter account, not tweeting about lunch '$lunch.topic'"
      return
    }

    if (!twitterUser) {
      log.debug "User $user.username isn't logged in with twitter, not tweeting about lunch '$lunch.topic'"
      return
    }

    def status = constructStatus(kind, user, lunch)

    if (ConfigurationHolder.config.twitter.sendTweets) {
      client.updateStatus status
    } else {
      log.debug "Not sending tweet: '$status'"
    }
  }

  private def constructLunchDetailsUrl(lunch) {
    ConfigurationHolder.config.grails.serverURL + "/lunch/show/$lunch.id"
  }

  private def constructStatus(kind, user, lunch) {
    def lunchDetailsUrl = constructLunchDetailsUrl(lunch)

    switch (kind) {
      case Kind.LUNCH_WITH_YOU:
        return "@${lunch.creator.twitterAcount.username} #wannalunch with you $lunchDetailsUrl"
      case Kind.LUNCH_WITH_ME:
        return "#wannalunch with me? See more information $lunchDetailsUrl"
      case Kind.LUNCH_WITH_EACH_OTHER:
        return "${user.twitterAcount.username} and @${lunch.creator.twitterAcount.username} #wannalunch $lunchDetailsUrl"
      default:
        throw new IllegalArgumentException("Unknown tweet kind " + tweetType)
    }
  }

}