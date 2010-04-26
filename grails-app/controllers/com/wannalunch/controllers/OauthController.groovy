package com.wannalunch.controllers

import twitter4j.Twitter

class OauthController {

  def twitterService

  def authorize = {
    def returnUrl = createLink(action: 'processLogin', absolute: true).toString()
    def requestToken = twitterService.generateRequestToken(returnUrl)
    redirect url: requestToken.authorizationURL
  }

  def processLogin = {
    log.debug "Processing Login Return from Twitter"

    if (!twitterService.loggedIn) {
      redirect action: 'authorize'
    } else {
      twitterService.validate(params.oauth_verifier)
      redirect controller: 'lunch'
    }
  }

  def logout = {
    log.debug "Logging out from Twitter"
    session.invalidate()
    redirect controller: 'lunch'
  }

}
