package com.wannalunch.domain

class City {

  String name
  
  static constraints = {
    name unique: true
  }
}
