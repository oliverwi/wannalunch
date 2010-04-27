package com.wannalunch.controllers

import com.wannalunch.domain.Lunch;
import com.wannalunch.domain.User;

class ProfileController {
  
  def userService
  
  def index = {
    User loggedInUser = userService.user
    [view: "index", user: loggedInUser, upcomingLunches: Lunch.findByParticipant(loggedInUser)]
  }
  
  def update = {
    User user = userService.user
    user.facebookProfile = getProfileLink(params.facebookProfile)
    user.linkedInProfile = getProfileLink(params.linkedInProfile)
    
    user.save()
    flash.message = "Profile updated!"
    
    redirect action: "index"
  }
  
  private def getProfileLink(link) {
    if (!link || link.trim() == "What's your profile link?") {
      return null
    }
    
    return link
  }
}
