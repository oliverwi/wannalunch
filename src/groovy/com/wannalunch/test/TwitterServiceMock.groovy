package com.wannalunch.test

import com.wannalunch.domain.User

class TwitterServiceMock {

  def user
  
  User getUserFromOauthToken(token, tokenSecret) {
    return user
  }
}
