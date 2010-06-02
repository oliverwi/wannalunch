package com.wannalunch.domain

class TwitterAccount {

  String username

  static belongsTo = [user: User]

  static constraints = {
    username blank: false, unique: true
  }
  
  def getProfileUrl() {
    return "http://twitter.com/${username}"
  }

}
