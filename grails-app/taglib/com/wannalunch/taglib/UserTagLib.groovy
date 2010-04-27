package com.wannalunch.taglib;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class UserTagLib implements ApplicationContextAware {

  static namespace = "user"

  ApplicationContext applicationContext
  
  def isLoggedIn = { attrs, body ->
    if (userService.isLoggedIn()) {
      out << body()
    }
  }

  def isNotLoggedIn = { attrs, body ->
    if (!userService.isLoggedIn()) {
      out << body()
    }
  }

  def userInfo = { attrs ->
    def field = attrs.remove("field")
    out << userService.user[field]
  }
  
  private def getUserService() {
    applicationContext.userService
  }
}
