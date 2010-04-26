package com.wannalunch.services

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import twitter4j.Twitter


class TwitterService {

  static scope = "session"

  boolean transactional = false

  def consumerKey = ConfigurationHolder.config.twitter.oauth.consumerKey
  def consumerSecret = ConfigurationHolder.config.twitter.oauth.consumerSecret

  def client = new Twitter()

  def requestToken
  def accessToken
  def user

  def generateRequestToken(returnUrl) {
    log.debug "Generating request with return url of [${returnUrl}]"
    client.setOAuthConsumer(consumerKey, consumerSecret)
    requestToken = client.getOAuthRequestToken(returnUrl)
  }

  def validate(oauthVerifierParam) {
    accessToken = client.getOAuthAccessToken(requestToken, oauthVerifierParam)
    log.debug "Attempting validate..."
    user = client.verifyCredentials()
    log.debug "Validate successful for ${user.screenName}"
  }

  boolean isLoggedIn() {
    requestToken != null
  }

}