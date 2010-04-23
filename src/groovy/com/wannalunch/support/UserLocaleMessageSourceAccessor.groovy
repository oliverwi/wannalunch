package com.wannalunch.support

import java.util.Locale


class UserLocaleMessageSourceAccessor {

  def messageSource

  String getMessage(String code) {
    getMessage(code, null)
  }

  String getMessage(String code, Locale locale) {
    messageSource.getMessage(code, null, locale ?: getLocale())
  }

  String getMessage(String code, Object arg, Locale locale) {
    messageSource.getMessage(code, [arg] as Object[], locale ?: getLocale())
  }

  String getMessage(String code, Object[] args, Locale locale) {
    messageSource.getMessage(code, args, locale ?: getLocale())
  }

  Locale getLocale() {
    Locale.ENGLISH
  }

}
