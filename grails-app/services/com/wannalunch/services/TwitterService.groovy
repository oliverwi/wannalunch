package com.wannalunch.services

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class TwitterService {

  def consumerKey = ConfigurationHolder.config.twitter.oauth.consumer_key
  def consumerSecret = ConfigurationHolder.config.twitter.oauth.consumer_secret

  def generateRequestToken(twitterClient, callbackUrl) {
    twitterClient.setOAuthConsumer(consumerKey, consumerSecret)
    twitterClient.getOAuthRequestToken(callbackUrl)
  }

}