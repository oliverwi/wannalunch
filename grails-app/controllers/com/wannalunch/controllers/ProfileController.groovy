package com.wannalunch.controllers

import com.wannalunch.aop.AuthRequired;
import java.io.File;

import com.wannalunch.domain.Lunch;
import com.wannalunch.domain.User;

@AuthRequired
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
    
    def uploadedImage = request.getFile("profileImage")
    if (!uploadedImage.empty) {
      
      def webRootDir = servletContext.getRealPath("/")
      def profileImage = new File("${webRootDir}/img/profile/${user.username}")
      println profileImage.absolutePath
      profileImage.mkdirs()
      uploadedImage.transferTo(profileImage)
      user.profileImageUrl = "${servletContext.contextPath}/img/profile/${user.username}"
    }
    
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
