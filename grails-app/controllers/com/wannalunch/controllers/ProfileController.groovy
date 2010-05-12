package com.wannalunch.controllers

import com.wannalunch.aop.AuthRequired;
import java.io.File;

import com.wannalunch.domain.Lunch;
import com.wannalunch.domain.User;

class ProfileController extends AbstractController {

  private static final VALID_IMAGE_TYPES = ["image/jpeg", "image/jpg", "image/png", "image/gif"]

  private static final MAX_IMAGE_SIZE = 307200

  def show = {
    def user = User.findByUsername(params.id)
    def upcomingLunches = user.findUpcomingLunches()
    [view: "show", user: user, upcomingLunches: upcomingLunches]
  }

  @AuthRequired
  def updateEmail = {
    def user = loggedInUser
    user.email = params.email

    if (user.save()) {
      render "success"
    } else {
      throw new RuntimeException("Failed to save e-mail")
    }
  }

  @AuthRequired
  def edit = {
    def user = loggedInUser
    def upcomingLunches = user.findUpcomingLunches()
    [view: "edit", user: user, upcomingLunches: upcomingLunches]
  }

  @AuthRequired
  def update = {
    User user = loggedInUser
    user.email = params.email
    user.linkedInProfile = params.linkedInProfile

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
