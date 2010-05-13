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

    redirect controller: 'lunch'
  }

  def facebookAuthorize = {
    userService.maybeCreateFacebookAccount('Timur Strekalov', FACEBOOK_ID,
      'http://profile.ak.fbcdn.net/v22940/419/87/q1275060355_3573.jpg', params.merge)

    redirect controller: 'lunch'
  }

  def logout = {
    session.invalidate()
    redirect controller: 'lunch'
  }
}