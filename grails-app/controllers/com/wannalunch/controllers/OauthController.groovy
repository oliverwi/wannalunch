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
    log.debug "Trying to authorize with Twitter"
    session.postAuthUrl = postAuthUrl

    def returnUrl = createLink(action: 'processLogin', absolute: true,
        params: params.merge ? [merge: params.merge] : [:]).toString()
    def requestToken = twitterService.generateRequestToken(returnUrl)
    def authUrl = requestToken.authorizationURL

    log.debug "Redirecting to $authUrl"

    redirect url: authUrl
  }

  def processLogin = {
    log.debug "Processing Login Return from Twitter"

    if (!twitterService.requestToken) {
      redirect action: 'authorize'
    } else {
      twitterService.validate(params.oauth_verifier, params.merge)

      def url = session.postAuthUrl
      session.removeAttribute("postAuthUrl")
      redirect url: url
    }
  }

  def facebookAuthorize = {
    log.debug "Trying to authorize with Facebook"
    session.postAuthUrl = postAuthUrl

    def helper = new FacebookWebappHelper(request, response, apiKey, apiSecret, facebookService.client)

    def next = createLink(controller: 'lunch', absolute: true,
        params: params.merge ? [merge: params.merge] : [:]).toString()
    redirect url: helper.getLoginUrl(next, false)
  }

  def facebookProcessLogin = {
    log.debug "Processing login response from Facebook"
    facebookService.sessionId = params.auth_token

    def merge = params.next.contains('merge=true')

    userService.maybeCreateFacebookAccount(facebookService.name, facebookService.userId,
        facebookService.profileImageUrl, merge)

    def url = session.postAuthUrl
    session.removeAttribute("postAuthUrl")
    redirect url: url
  }

  def logout = {
    log.debug "Logging out"
    session.invalidate()
    redirect controller: 'lunch'
  }

  private String getPostAuthUrl() {
    log.debug "Getting post-auth URL from referer"

    def url = request.getHeader("referer")

    if (!url) {
      log.debug "Couldn't get URL from referer, redirecting to the default page instead"

      url = createLink(controller: 'lunch', absolute: true)
    }

    log.debug "Post-auth URL is $url"

    return url
  }

}
