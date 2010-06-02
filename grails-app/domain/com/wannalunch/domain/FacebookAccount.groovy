package com.wannalunch.domain

class FacebookAccount {

  long userId

  static belongsTo = [user: User]

  static constraints = {
    userId unique: true
  }
  
  def getProfileUrl() {
    return "http://www.facebook.com/profile.php?id=${userId}"
  }
}
