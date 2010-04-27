package com.wannalunch.controllers

import com.wannalunch.domain.Lunch;
import com.wannalunch.domain.User;

class ProfileController {
  
  def userService
  
  def show = {
    User user = User.findByUsername(params.id)
    [view: "show", user: user, upcomingLunches: Lunch.findByParticipant(user)]
  }
  
  def edit = {
    User loggedInUser = userService.user
    [view: "edit", user: loggedInUser, upcomingLunches: Lunch.findByParticipant(loggedInUser)]
  }
  
  def update = {
    User user = userService.user
    user.facebookProfile = getProfileLink(params.facebookProfile)
    user.linkedInProfile = getProfileLink(params.linkedInProfile)
    
    user.save()
    flash.message = "Profile updated!"
    
    redirect action: "edit"
  }
  
  private def getProfileLink(link) {
    if (!link || link.trim() == "What's your profile link?") {
      return null
    }
    
    return link
  }
}
