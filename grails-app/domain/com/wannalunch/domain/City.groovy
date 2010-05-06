package com.wannalunch.domain

class City {

  String name
  
  static constraints = {
    name unique: true
  }
  
  boolean equals(Object o) {
    if (!(o instanceof City)) {
      return false
    }
    
    return ((City) o).name.equals(name)
  }
  
  int hashCode() {
    return name.hashCode()
  }
}
