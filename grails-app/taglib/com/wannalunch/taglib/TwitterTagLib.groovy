package com.wannalunch.taglib

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

class TwitterTagLib implements ApplicationContextAware {

  static namespace = "twitter"

  ApplicationContext applicationContext

  def isLoggedIn = { attrs, body ->
    if (twitterService.loggedIn) {
      out << body()
    }
  }

  def isNotLoggedIn = { attrs, body ->
    if (!twitterService.loggedIn) {
      out << body()
    }
  }

  def userInfo = { attrs ->
    def field = attrs.remove("field")
    out << twitterService.user[field]
  }

  private def getTwitterService() {
    applicationContext.twitterService
  }

}
