package com.wannalunch.domain

class Luncher {

  User user
  
  boolean wantsNotification = true
  
  def getName() {
    return user.name
  }
  
  def getUsername() {
    return user.username
  }
  
  def getEmail() {
    return user.email
  }
  
  def getProfileImageUrl() {
    return user.profileImageUrl
  }
  
  def getFacebookProfile() {
    return user.facebookProfile
  }
  
  def getLinkedInProfile() {
    return user.linkedInProfile
  }
  
  def setEmail(String email) {
    user.setEmail(email)
  }
  
  def create(lunch) {
    lunch.creator = this
    user.create(lunch)
  }
  
  def promoteToParticipant(applicant, lunch) {
    user.promoteToParticipant(applicant, lunch)
  }
  
  boolean equals(Object o) {
    if (!(o instanceof Luncher)) {
      return false
    }
    
    Luncher other = (Luncher) o 
    return user.equals(other.user)
  }
  
  int hashCode() {
    return user.hashCode()
  }
}
