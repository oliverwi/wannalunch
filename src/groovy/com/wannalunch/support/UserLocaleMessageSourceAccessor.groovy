package com.wannalunch.support

import java.util.Locale


class UserLocaleMessageSourceAccessor {

  def messageSource

  String getMessage(String code) {
    messageSource.getMessage(code, null, getLocale())
  }

  String getMessage(String code, Object[] args) {
    messageSource.getMessage(code, args, getLocale())
  }

  Locale getLocale() {
    Locale.ENGLISH
  }

}
