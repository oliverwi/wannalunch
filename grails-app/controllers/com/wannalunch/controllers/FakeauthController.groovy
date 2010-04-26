package com.wannalunch.controllers

class FakeauthController {
  
  def userService
  
  def twitterService
  
  def authorize = {
    def twitterUser = new FakeTwitterUser(name: "Oliver Wihler", screenName: "oliverwi")    
    userService.maybeCreateAccount(twitterUser)
    twitterService.twitterUser = twitterUser
    
    redirect controller: 'lunch'
  }
  
  def logout = {
    session.invalidate()
    
    redirect controller: 'lunch'
  }
}

class FakeTwitterUser {
  String screenName  
  String name
}
