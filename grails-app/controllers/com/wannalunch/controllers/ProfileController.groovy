package com.wannalunch.controllers

import com.wannalunch.domain.Lunch;
import com.wannalunch.domain.User;

class ProfileController {
  
  def userService
  
  def index = {
    User loggedInUser = userService.user
    [view: "index", upcomingLunches: Lunch.findByParticipant(loggedInUser)]
  }
}
