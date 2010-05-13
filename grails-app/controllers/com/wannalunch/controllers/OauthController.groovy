package com.wannalunch.controllers

import org.codehaus.groovy.grails.commons.ConfigurationHolder;

import com.facebook.api.FacebookWebappHelper

class OauthController {

  def twitterService
  def facebookService

  def userService

  def apiKey = ConfigurationHolder.config.facebook.oauth.apiKey
  def apiSecret = ConfigurationHolder.config.facebook.oauth.apiSecret

  def authorize = {
    def returnUrl = createLink(action: 'processLogin', absolute: true,
        params: params.merge ? [merge: params.merge] : [:]).toString()
    def requestToken = twitterService.generateRequestToken(returnUrl)
    redirect url: requestToken.authorizationURL
  }

  def processLogin = {
    log.debug "Processing Login Return from Twitter"

    if (!twitterService.requestToken) {
      redirect action: 'authorize'
    } else {
      twitterService.validate(params.oauth_verifier, params.merge)
      redirect controller: 'lunch'
    }
  }

  def facebookAuthorize = {
    log.debug "Trying to authorize with Facebook"
    def helper = new FacebookWebappHelper(request, response, apiKey, apiSecret, facebookService.client)

    // TODO maybe get next url from referer
    def next = createLink(controller: 'lunch', absolute: true,
        params: params.merge ? [merge: params.merge] : [:]).toString()
    redirect url: helper.getLoginUrl(next, false)
  }

  def facebookProcessLogin = {
    log.debug "Processing login response from Facebook"
    facebookService.sessionId = params.auth_token

    def nextUrl = params.next

    def merge = nextUrl.contains('merge=true')

    userService.maybeCreateFacebookAccount(facebookService.name, facebookService.userId,
        facebookService.profileImageUrl, merge)

    redirect url: nextUrl.substring(0, merge ? nextUrl.indexOf('merge') : nextUrl.length())
  }

  def logout = {
    log.debug "Logging out"
    session.invalidate()
    redirect controller: 'lunch'
  }

}
