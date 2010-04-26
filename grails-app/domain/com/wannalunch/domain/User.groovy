package com.wannalunch.domain

import java.io.Serializable

class User implements Serializable {

  String name

  String username

  static mappings = {
    username blank: false
  }

}
