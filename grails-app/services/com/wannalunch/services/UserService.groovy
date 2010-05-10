package com.wannalunch.services

import com.wannalunch.domain.City;
import com.wannalunch.domain.User

class UserService {

  static scope = "session"

  def user
  
  def city

  def maybeCreateAccount(twitterUser) {
    def username = twitterUser.screenName
    user = User.findByUsername(username)

    if (!user) {
      def name = twitterUser.name
      def profileImageUrl = twitterUser.profileImageURL.toString()
      user = new User(username: username, name: name, profileImageUrl: profileImageUrl).save()
    }
  }

  boolean isLoggedIn() {
    return user != null
  }
}
