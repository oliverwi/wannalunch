package com.wannalunch.domain

import java.io.Serializable

class User implements Serializable {
	
	String name
	
	String username
	
	String profileImageUrl
	
	String facebookProfile
	
	String linkedInProfile
	
	static constraints = {
		username blank: false
		facebookProfile nullable: true
		linkedInProfile nullable: true
	}
	
	boolean applyTo(Lunch lunch) {
	  lunch.addToApplicants(this)
	  return lunch.save()
	}
	
	boolean cancelParticipation(Lunch lunch) {
	  if (isApplicantOf(lunch)) {
	    lunch.removeFromApplicants(this)
	    return lunch.save()
	  }
	  if (isParticipantOf(lunch)) {
	    lunch.removeFromParticipants(this)
	    return lunch.save()
	  }
	  return false
	}
	
	boolean promoteToParticipant(User applicant, Lunch lunch) {
	  if (!isCreatorOf(lunch) || !applicant.isApplicantOf(lunch)) {
	    return false
	  }
	  
	  lunch.removeFromApplicants(applicant)
	  lunch.addToParticipants(applicant)
	  
	  return lunch.save()
	}
	
	boolean isCreatorOf(Lunch lunch) {
	  lunch.creator == this
	}
	
	boolean isApplicantOf(Lunch lunch) {
	  lunch.applicants.contains(this)
	}
	
	boolean isParticipantOf(Lunch lunch) {
	  lunch.participants.contains(this)
	}
	
	boolean isAttending(Lunch lunch) {
	  isCreatorOf(lunch) || isApplicantOf(lunch) || isParticipantOf(lunch)
	}
	
	public boolean equals(Object o) {
	  if (!(o instanceof User)) {
	    return false
	  }
	  
	  User user = (User) o
	  return username.equals(user.username)
	}
	
	public int hashCode() {
	  username.hashCode()
	}
}
