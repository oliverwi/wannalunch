package com.wannalunch.domain

class FacebookAccount {

  long userId

  static belongsTo = [user: User]

  static constraints = {
    userId unique: true
  }

}
