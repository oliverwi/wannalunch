package com.wannalunch.controllers

import com.wannalunch.aop.AuthRequired;
import com.wannalunch.domain.Lunch
import com.wannalunch.domain.User

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

class LunchController {

  static defaultAction = "upcomingLunches"

  def userService

  def lunchService

  def upcomingLunches = {
    def city = userService.city
    def upcomingLunches = Lunch.findUpcomingLunchesInCity(city, paginateParams)
    def total = Lunch.countUpcomingLunchesInCity(city)

    render(view: "browse", model: [upcomingLunches: upcomingLunches, totalUpcomingLunches: total])
  }

  def freshlyAddedLunches = {
    def city = userService.city
    def upcomingLunches = Lunch.findFreshlyAddedLunchesInCity(city, paginateParams)
    def total = Lunch.countUpcomingLunchesInCity(city)

    render(view: "browse", model: [upcomingLunches: upcomingLunches, totalUpcomingLunches: total])
  }

  def show = {
    def id = params.id ?: firstLunchId
    def lunch = Lunch.get(id)
    def user = userService.user

    [lunch: lunch,
    showLunchButton: userService.isLoggedIn() ? user.canApplyTo(lunch) : true,
    showNotGoingButton: userService.isLoggedIn() && user.canBeRemovedFrom(lunch),
    showDeleteButton: userService.isLoggedIn() && user.canDelete(lunch),
    canAcceptApplicants: userService.isLoggedIn() && user.canAcceptApplicantsFor(lunch)]
  }

  def next = {
    def currentLunch = Lunch.get(params.id)
    redirect action: show, id: currentLunch.nextUpcomingLunch.id
  }

  def previous = {
    def currentLunch = Lunch.get(params.id)
    redirect action: show, id: currentLunch.previousUpcomingLunch.id
  }

  @AuthRequired
  def apply = {
    def lunch = Lunch.get(params.id)
    def user = userService.user

    if (lunchService.applyTo(user, lunch)) {
      redirect action: "show", id: lunch.id
    } else {
      throw new RuntimeException("Oops!")
    }
  }

  @AuthRequired
  def leave = {
    def lunch = Lunch.get(params.id)
    def user = userService.user

    if (user.cancelParticipation(lunch)) {
      redirect action: "show", id: lunch.id
    } else {
      throw new RuntimeException("Oops!")
    }
  }

  @AuthRequired
  def delete = {
    def lunch = Lunch.get(params.id)
    def user = userService.user

    lunch.delete(flush: true)
    redirect action: "upcomingLunches"
  }

  @AuthRequired
  def accept = {
    def lunch = Lunch.get(params.id)
    def applicant = User.findByUsername(params.username)

    if (lunchService.promoteToParticipant(applicant, lunch)) {
      redirect action: "show", id: lunch.id
    } else {
      throw new RuntimeException("Oops!")
    }
  }

  @AuthRequired
  def comment = {
    def lunch = Lunch.get(Long.parseLong(params.lunch))
    def author = userService.user
    def text = params.text

    def comment = lunchService.comment(lunch, author, text)

    if (comment) {
      redirect action: "show", id: lunch.id
    } else {
      throw new RuntimeException(comment.errors)
    }
  }

  @AuthRequired
  def create = {
    def lunch = new Lunch()
    lunch.creator = userService.user

    [lunch: lunch]
  }

  @AuthRequired
  def save = {
    def lunch = new Lunch()
    lunch.properties = params
    
    lunch.city = userService.city
    lunch.creatorWantsNotifications = new Boolean(params.creatorWantsNotifications)
    lunch.createDateTime = new LocalDateTime()
    
    if (lunchService.createLunch(userService.user, lunch)) {
      redirect action: "show", id: lunch.id
    } else {
      render(view: "create", model: [lunch: lunch])
    }
  }

  private def getFirstLunchId() {
    Lunch.find("from Lunch where date >= :today order by date, time",
    [today: new LocalDate()]).id
  }

  private def getLastLunchId() {
    Lunch.find("from Lunch where date >= :today order by date desc, time desc",
    [today: new LocalDate()]).id
  }

  private def getPaginateParams() {
    int max = params.max ? params.max.toInteger() : 10
    int offset = params.offset ? params.offset.toInteger() : 0

    [max: max, offset: offset]
  }
}
