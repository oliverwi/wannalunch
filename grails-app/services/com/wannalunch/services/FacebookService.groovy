package com.wannalunch.services

import com.facebook.api.FacebookJsonRestClient
import com.facebook.api.ProfileField;

import java.io.Serializable;

import org.codehaus.groovy.grails.commons.ConfigurationHolder;

class FacebookService implements Serializable {

  static scope = "session"

  boolean transactional = false

  FacebookJsonRestClient client = [ConfigurationHolder.config.facebook.oauth.apiKey,
                                   ConfigurationHolder.config.facebook.oauth.apiSecret]

  def getUserId() {
    client.users_getLoggedInUser()
  }

  def getName() {
    def userId = getUserId()
    def jsonArray = client.users_getInfo([userId], [ProfileField.NAME])
    return jsonArray.get(0).get('name')
  }

  def getProfileImageUrl() {
    def userId = getUserId()
    def jsonArray = client.users_getInfo([userId], [ProfileField.PIC_SQUARE])
    return jsonArray.get(0).get('pic_square')
  }

  void setSessionId(authToken) {
    client.auth_getSession authToken
  }

}
