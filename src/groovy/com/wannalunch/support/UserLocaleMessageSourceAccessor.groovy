package com.wannalunch.support

import java.util.List;
import java.util.Locale

class UserLocaleMessageSourceAccessor {

  def messageSource

  String getMessage(String code) {
    messageSource.getMessage(code, null, getLocale())
  }

  String getMessage(String code, List args) {
    messageSource.getMessage(code, args.toArray(new Object[args.size()]), getLocale())
  }

  Locale getLocale() {
    Locale.ENGLISH
  }

}
