package com.wannalunch.taglib

import org.codehaus.groovy.grails.commons.ConfigurationHolder;
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

class TwitterTagLib implements ApplicationContextAware {

  static namespace = "twitter"

  ApplicationContext applicationContext
  
  def config = ConfigurationHolder.config

  def loginLink = {
    out << "${config.grails.serverURL}/${config.twitter.authController}/authorize"
  }
  
  def logoutLink = {
    out << "${config.grails.serverURL}/${config.twitter.authController}/logout"
  }
  
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
  
  def linkToProfile = { attrs ->
    def user = attrs.remove("user")
    out << "http://twitter.com/${user.username}"
  }

  private def getTwitterService() {
    applicationContext.twitterService
  }
  
  private def getUserService() {
    applicationContext.userService
  }
}
