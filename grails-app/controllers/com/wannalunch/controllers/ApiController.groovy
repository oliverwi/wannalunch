package com.wannalunch.controllers

import org.joda.time.LocalDateTime;

import com.wannalunch.converters.JSONP;
import com.wannalunch.domain.City;
import com.wannalunch.domain.Comment;
import com.wannalunch.domain.Lunch;
import com.wannalunch.domain.User;
import com.wannalunch.domain.TwitterAccount;
import com.wannalunch.domain.Lunch.PaymentOption;

class ApiController {

  def twitterService

  def facebookService

  def userService

  def lunchService

  def login = {
    log.debug "Processing login through web service API"
    def authType = params.authType
    if ("twitter" == authType) {
      def twitterUser = twitterService.getTwitterUserFromOauthToken(params.token, params.tokenSecret)
      userService.maybeCreateTwitterAccount(twitterUser, false)
    }

    render(["OK"] as JSONP)
  }

  def newLunch = {
    User creator = getAuthorizedUser(params)
    Lunch lunch = new Lunch()
    lunch.properties = params
    lunch.city = City.findByName(params.cityName)
    lunch.paymentOption = Lunch.PaymentOption.fromCode(params.whoPays)

    lunch.creatorWantsNotifications = true
    lunch.createDateTime = new LocalDateTime()

    if (lunchService.createLunch(creator, lunch)) {
      render(lunch.toJsonArray() as JSONP)
    } else {
      throw new RuntimeException("A problem happened trying to create lunch through API.")
    }
  }

  def joinLunch = {
    User user = getAuthorizedUser(params)
    Lunch lunch = Lunch.get(params.lunchId)

    if (lunchService.applyTo(user, lunch)) {
      render(["OK"] as JSONP)
    } else {
      throw new RuntimeException("A problem happened trying to join a lunch through API.")
    }
  }

  def accept = {
    User creator = getAuthorizedUser(params)
    Lunch lunch = Lunch.get(params.lunchId)
    User applicant = User.findByUsername(params.username)

    if (lunchService.promoteToParticipant(applicant, lunch)) {
      render(["OK"] as JSONP)
    } else {
      throw new RuntimeException("A problem happened trying to accept an user to a lunch through API.")
    }
  }

  def comment = {
    Lunch lunch = Lunch.get(params.lunchId)
    User author = getAuthorizedUser(params)
    String text = params.text

    Comment comment = lunchService.comment(lunch, author, text)

    if (comment) {
      render(["OK"] as JSONP)
    } else {
      throw new RuntimeException("A problem happened trying to post a comment to a lunch through API.")
    }
  }

  private User getAuthorizedUser(params) {
    def authType = params.authType ?: ""
    switch (authType) {
      case "facebook":
        throw new RuntimeException("Not yet implemented.")

      case "twitter":
        return twitterService.getUserFromOauthToken(params.token, params.tokenSecret)
    }

    throw new RuntimeException("parameter 'authType' invalid")
  }
}
