package com.wannalunch.taglib

import org.joda.time.format.DateTimeFormat;

class FormatTagLib {

  static namespace = "f"
  
  def date = { attrs ->
    def date = attrs.value
    def dayOrdinal = getNumberOrdinal(date.dayOfMonth)
    def formatter = DateTimeFormat.forPattern("EEEE, d'$dayOrdinal' 'of' MMMM")
    
    out << formatter.print(date)
  }
  
  private def getNumberOrdinal(number) {
    if (number >= 4  && number <= 20) {
      return "th"
    }
    
    switch (number % 10) {
      case 1:
        return "st"
      
      case 2:
        return "nd"
        
      case 3:
        return "rd"
        
      default:
        return "th"
    }
  }
}
