package com.wannalunch.services

import java.io.Serializable;

import com.wannalunch.domain.Comment
import com.wannalunch.domain.FacebookAccount;
import com.wannalunch.domain.Lunch
import com.wannalunch.domain.TwitterAccount
import com.wannalunch.domain.User

class UserService implements Serializable {

  static scope = "session"

  def user

  def city

  def maybeCreateTwitterAccount(twitterUser, merge) {
    log.debug "Maybe creating twitter account for $twitterUser.screenName"
    def username = twitterUser.screenName
    def account = TwitterAccount.findByUsername(username)

    if (!account) {
      log.debug "No account found for username = $username, creating one"
      account = new TwitterAccount(username: username)
    } else if (!merge) {
      user = account.user
      return user
    }

    if (merge) {
      mergeTwitterAccount account
    } else {
      def name = twitterUser.name
      def profileImageUrl = twitterUser.profileImageURL.toString()
      user = new User(username: username, name: name, profileImageUrl: profileImageUrl, twitterAccount: account).save()
    }

    return user
  }

  def maybeCreateFacebookAccount(name, userId, profileImageUrl, merge) {
    log.debug "Maybe creating FB account for $name with id $userId and profile pic url $profileImageUrl"

    def account = FacebookAccount.findByUserId(userId)

    if (!account) {
      log.debug "No account found for id = $userId, creating one"
      account = new FacebookAccount(userId: userId)
    } else if (!merge) {
      user = account.user
      return user
    }

    if (merge) {
      mergeFacebookAccount account
    } else {
      def username = name.replaceAll(/\s+/, '.').toLowerCase()
      user = new User(username: username, name: name, profileImageUrl: profileImageUrl, facebookAccount: account).save()
    }

    return user
  }

  def mergeTwitterAccount(account) {
    def currentUser = getUser()

    assert currentUser, "Must be logged in in order to merge"

    log.debug "Merging twitter account for username $account.username"

    def oldUser = account.user

    currentUser.twitterAccount = account
    account.user = currentUser

    if (oldUser) {
      oldUser.twitterAccount = null
      migrateUserData(oldUser, currentUser)
    }

    return currentUser
  }

  def mergeFacebookAccount(account) {
    def currentUser = getUser()

    assert currentUser, "Must be logged in in order to merge"

    log.debug "Merging FB account for user id $account.userId"

    def oldUser = account.user

    currentUser.facebookAccount = account
    account.user = currentUser

    if (oldUser) {
      oldUser.facebookAccount = null
      migrateUserData(oldUser, currentUser)
    }

    return currentUser
  }

  private def migrateUserData(oldUser, currentUser) {
    migrateDetails(oldUser, currentUser)
    migrateAssociations(oldUser, currentUser)

    user = currentUser.save()

    oldUser.delete()

    return currentUser
  }

  private void migrateDetails(oldUser, currentUser) {
    if (!currentUser.email) {
      currentUser.email = oldUser.email
    }

    if (!currentUser.linkedInProfile) {
      currentUser.linkedInProfile = oldUser.linkedInProfile
    }
  }

  private void migrateAssociations(oldUser, currentUser) {
    migrateOwnLunches(oldUser, currentUser)
    migrateLunchApplications(oldUser, currentUser)
    migrateLunchParticipations(oldUser, currentUser)
    migrateComments(oldUser, currentUser)
  }

  private void migrateOwnLunches(oldUser, currentUser) {
    log.debug "Migrating own lunches"

    Lunch.createCriteria().list {
      eq 'creator', oldUser
    }.each { lunch ->
      lunch.creator = currentUser
      lunch.save()
    }
  }

  private void migrateLunchApplications(oldUser, currentUser) {
    log.debug "Migrating lunch applications"

    Lunch.createCriteria().list {
      applicants {
        eq 'id', oldUser.id
      }
    }.each { lunch ->
      lunch.removeFromApplicants(oldUser)
      lunch.addToApplicants(currentUser)
      lunch.save()
    }
  }

  private void migrateLunchParticipations(oldUser, currentUser) {
    log.debug "Migrating lunch participations"

    Lunch.createCriteria().list {
      participants {
        eq 'id', oldUser.id
      }
    }.each { lunch ->
      lunch.removeFromParticipants(oldUser)
      lunch.addToParticipants(currentUser)
      lunch.save()
    }
  }

  private void migrateComments(oldUser, currentUser) {
    log.debug "Migrating comments"

    Comment.createCriteria().list {
      eq 'author', oldUser
    }.each { comment ->
      comment.author = currentUser
      comment.save()
    }
  }

  def getUser() {
    User.get(user.id)
  }

  boolean isLoggedIn() {
    return user != null
  }
}
