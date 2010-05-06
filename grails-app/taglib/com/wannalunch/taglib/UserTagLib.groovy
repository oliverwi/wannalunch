package com.wannalunch.taglib;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.wannalunch.domain.City;

public class UserTagLib implements ApplicationContextAware {

  static namespace = "u"

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
  
  def city = { attrs ->
    out << userService.city.name
  }
  
  def availableCities = {
    City.findAll().each {
      if (it.equals(userService.city)) {
        out << "<a class=\"selected clearLink\">"
      } else {
        out << "<a href=\"${g.createLink(controller: 'city', action: 'change', id: it.id)}\">"
      }
      out << "${it.name}</a>\n"
    }
  }
  
  private def getUserService() {
    applicationContext.userService
  }
}
