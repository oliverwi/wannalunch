package com.wannalunch.services

import com.facebook.api.FacebookJsonRestClient
import com.facebook.api.ProfileField;
import com.wannalunch.domain.FacebookAccount;
import com.wannalunch.domain.User;

import java.io.Serializable;

import org.codehaus.groovy.grails.commons.ConfigurationHolder;

class FacebookService implements Serializable {

  static scope = "session"

  boolean transactional = false
  
  String apiKey = ConfigurationHolder.config.facebook.oauth.apiKey.toString()
  String apiSecret = ConfigurationHolder.config.facebook.oauth.apiSecret.toString()
  
  FacebookJsonRestClient client = new FacebookJsonRestClient(apiKey, apiSecret)

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
    log.info "RETURNING FROM FACEBOOK AUTH TOKEN IS " + authToken 
  }
  
  User getUserFromOauthToken(String accessToken) {
    client.auth_getSession(accessToken)
    FacebookAccount account = FacebookAccount.findByUserId(client.users_getLoggedInUser())
    if (account) {
      return account.user
    }
    return null
  }

}
