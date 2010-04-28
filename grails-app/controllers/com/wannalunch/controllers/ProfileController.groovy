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
    user.facebookProfile = params.facebookProfile
    user.linkedInProfile = params.linkedInProfile

    def uploadedImage = request.getFile("profileImage")
    if (!uploadedImage.empty) {
      user.profileImageUrl = uploadAndReturnUrl(uploadedImage, user.username)
    }

    user.save()
    flash.message = "Profile updated!"

    redirect action: "edit"
  }

  private def uploadAndReturnUrl(uploadedImage, newFileName) {
    def webRootDir = servletContext.getRealPath("/")
    def profileImageUrl = "${webRootDir}/img/profile/${newFileName}"

    def profileImage = new File(profileImageUrl)
    profileImage.mkdirs()
    uploadedImage.transferTo(profileImage)

    return "${servletContext.contextPath}/img/profile/${newFileName}"
  }
}
