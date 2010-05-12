package com.wannalunch.domain

class TwitterAccount {

  String username

  static belongsTo = User

  static constraints = {
    username blank: false, unique: true
  }

}
