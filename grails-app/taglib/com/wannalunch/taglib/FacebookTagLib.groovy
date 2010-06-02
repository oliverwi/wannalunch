package com.wannalunch.taglib

import org.codehaus.groovy.grails.commons.ConfigurationHolder;
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware


class FacebookTagLib implements ApplicationContextAware {

  static namespace = "facebook"

  ApplicationContext applicationContext

  def config = ConfigurationHolder.config

  def loginLink = { attrs ->
    def merge = attrs.merge ? '?merge=true' : ''
    out << "${config.grails.serverURL}/${config.authController}/facebookAuthorize$merge"
  }

  def logoutLink = {
    out << "${config.grails.serverURL}/${config.authController}/logout"
  }

  def linkToProfile = { attrs ->
    def user = attrs.remove("user")
    out << user.facebookAccount.profileUrl
  }

  private def getFacebookService() {
    applicationContext.facebookService
  }
}
