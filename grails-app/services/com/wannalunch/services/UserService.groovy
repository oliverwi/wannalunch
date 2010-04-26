package com.wannalunch.services

import com.wannalunch.domain.User

class UserService {

  static scope = "session"

  def user

  def maybeCreateAccount(twitter4j.User twitterUser) {
    def username = twitterUser.screenName
    user = User.findByUsername(username)

    if (!user) {
      def name = twitterUser.getName()
//      def profileImageUrl = user.getProfileImageURL().toString()
      user = new User(username: username, name: name).save()
    }
  }

}
