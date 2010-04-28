package com.wannalunch.controllers

import com.wannalunch.aop.AuthRequired;
import java.io.File;

import com.wannalunch.domain.Lunch;
import com.wannalunch.domain.User;

@AuthRequired
class ProfileController {
  
  private static final VALID_IMAGE_TYPES = ["image/jpeg", "image/jpg", "image/png", "image/gif"]

  private static final MAX_IMAGE_SIZE = 307200

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
      if (!isValid(uploadedImage)) {
        redirect action: "edit"
        return
      }
      
      user.profileImageUrl = uploadAndReturnUrl(uploadedImage, user.username)
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
  
  private def isValid(uploadedImage) {
    if (!(uploadedImage.contentType in VALID_IMAGE_TYPES)) {
      flash.message = "Only 'png', 'jpg', 'jpeg' and 'gif' files are allowed"
      return false
    }
    
    if (uploadedImage.size > MAX_IMAGE_SIZE) {
      flash.message = "The maximum picture file size is 300kB"
      return false
    }
    
    return true
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
