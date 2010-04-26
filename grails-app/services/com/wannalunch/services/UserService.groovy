package com.wannalunch.services

import com.wannalunch.domain.User

class UserService {

  static scope = "session"

  def user

  def maybeCreateAccount(twitterUser) {
    def username = twitterUser.screenName
    user = User.findByUsername(username)

    if (!user) {
      def name = twitterUser.name
//      def profileImageUrl = user.getProfileImageURL().toString()
      user = new User(username: username, name: name).save()
    }
  }
  
  boolean isLoggedIn() {
    return user != null
  }

}
