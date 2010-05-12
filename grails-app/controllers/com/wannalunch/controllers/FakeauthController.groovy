package com.wannalunch.controllers

import com.wannalunch.domain.User;

class FakeauthController {

  def userService

  def authorize = {
    def user = User.findAll()[0]
    userService.user = user

    redirect controller: 'lunch'
  }

  def logout = {
    session.invalidate()
    userService.user = null

    redirect controller: 'lunch'
  }
}