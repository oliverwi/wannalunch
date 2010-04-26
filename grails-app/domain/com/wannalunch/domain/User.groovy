package com.wannalunch.domain

import java.io.Serializable

class User implements Serializable {

  String name

  String username
  
  String profileImageUrl

  static mappings = {
    username blank: false
  }
}
