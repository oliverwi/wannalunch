package com.wannalunch.controllers

import java.net.URL;

import com.wannalunch.domain.City;
import com.wannalunch.domain.User;

abstract class AbstractController {

  def userService

  boolean isLoggedIn() {
    userService.isLoggedIn()
  }

  User getLoggedInUser() {
    isLoggedIn() ? userService.getUser() : null
  }

  void setCity(City city) {
    userService.city = city
  }

  City getCity() {
    userService.city
  }
}
