package com.wannalunch.controllers

import twitter4j.Twitter

class Oauth {

  def twitterService

  def authorize = {
    def twitterClient = new Twitter()
    def returnUrl = g.createLink(controller: 'oauth', action: 'processLogin', absolute: true).toString()
    log.debug "Generating request with return url of [${returnUrl}]"
    def requestToken = twitterService.generateRequestToken(twitterClient, returnUrl)
    session.twitterClient = twitterClient
    session.requestToken = requestToken
    redirect url: requestToken.getAuthorizationURL()
  }

  def processLogin = {
    log.debug "Processing Login Return from Twitter"

    if (!session.requestToken) {
      redirect(action: 'authorize')
    } else {
      def accessToken = session.twitter.getOAuthAccessToken(session.requestToken, params.oauth_verifier)
      log.debug "Attempting validate..."
      def twitterUser = session.twitter.verifyCredentials()
      log.debug "Validate successful for ${twitterUser.screenName}"
      session.twitterUser = twitterUser
      redirect action: 'displayDetails'
    }
  }

  def logout = {
    log.debug "Logging out from Twitter"
    session.twitterUser = null
    session.twitterClient = null
    session.requestToken = null
  }

}
