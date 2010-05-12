package com.wannalunch.domain

class FacebookAccount {

  String username

  String profileUrl

  static belongsTo = User

  static constraints = {
    username blank: false, unique: true
  }

}
