package com.wannalunch.controllers

import com.wannalunch.domain.Lunch;
import com.wannalunch.domain.User;

class ProfileController {
  
  def userService
  
  def show = {
    def user = User.findByUsername(params.id)
    def upcomingLunches = Lunch.findWhereCreatorOrParticipant(user)
    [view: "show", user: user, upcomingLunches: upcomingLunches]
  }
  
  def edit = {
    def loggedInUser = userService.user
    def upcomingLunches = Lunch.findWhereCreatorOrParticipant(loggedInUser)
    [view: "edit", user: loggedInUser, upcomingLunches: upcomingLunches]
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
