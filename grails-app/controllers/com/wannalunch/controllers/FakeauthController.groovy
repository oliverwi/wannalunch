package com.wannalunch.controllers

import com.wannalunch.domain.User;

class FakeauthController {
  
  def userService
  
  def authorize = {
    def user = User.findByUsername("oliverwi")
    userService.user = user
    
    redirect controller: 'lunch'
  }
  
  def logout = {
    session.invalidate()
    
    redirect controller: 'lunch'
  }
}