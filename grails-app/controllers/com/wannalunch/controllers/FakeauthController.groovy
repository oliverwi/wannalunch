package com.wannalunch.controllers

import com.wannalunch.domain.User;

class FakeauthController {

  private static final String TWITTER_USERNAME = 'timurstrekalov'
  private static final long FACEBOOK_ID = 1275060355

  def userService

  def authorize = {
    userService.maybeCreateTwitterAccount([name: 'Timur Strekalov',
                                    screenName: TWITTER_USERNAME,
                                    profileImageURL: 'http://a1.twimg.com/profile_images/755799796/userpic.jpeg'],
                                    params.merge)

    redirect url: postAuthUrl
  }

  def facebookAuthorize = {
    userService.maybeCreateFacebookAccount('Timur Strekalov', FACEBOOK_ID,
      'http://profile.ak.fbcdn.net/v22940/419/87/q1275060355_3573.jpg', params.merge)

    redirect url: postAuthUrl
  }

  def logout = {
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