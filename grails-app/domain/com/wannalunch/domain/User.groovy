package com.wannalunch.domain

import java.io.Serializable

class User implements Serializable {

  String name

  String username
  
  String profileImageUrl
  
  String facebookProfile
  
  String linkedInProfile

  static constraints = {
    username blank: false
    facebookProfile nullable: true
    linkedInProfile nullable: true
  }
}
