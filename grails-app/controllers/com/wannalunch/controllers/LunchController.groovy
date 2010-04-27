package com.wannalunch.controllers

import com.wannalunch.aop.AuthRequired;
import com.wannalunch.domain.Comment;
import com.wannalunch.domain.Lunch
import com.wannalunch.domain.User

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime
import org.joda.time.LocalTime;

class LunchController {

  static defaultAction = "show"

  def userService

  def show = {
    def id = params.id ?: firstLunchId
    def lunch = Lunch.get(id)
    def user = userService.user

    [lunch: lunch,
     nextId: getNextLunchId(lunch),
     showLunchButton: userService.isLoggedIn() ? user.canApplyTo(lunch) : true,
     showNotGoingButton: userService.isLoggedIn() && user.canBeRemovedFrom(lunch),
     showDeleteButton: userService.isLoggedIn() && user.canDelete(lunch),
     canAcceptApplicants: userService.isLoggedIn() && user.canAcceptApplicantsFor(lunch)]
  }

  @AuthRequired
  def apply = {
    def lunch = Lunch.get(params.id)
    def user = userService.user

    if (user.applyTo(lunch)) {
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
    redirect action: "show"
  }

  @AuthRequired
  def accept = {
    def lunch = Lunch.get(params.id)
    def creator = userService.user
    def applicant = User.findByUsername(params.username)

    if (creator.promoteToParticipant(applicant, lunch)) {
      redirect action: "show", id: lunch.id
    } else {
      throw new RuntimeException("Oops!")
    }
  }

  @AuthRequired
  def comment = {
    def lunch = Lunch.get(Long.parseLong(params.lunch))

    def comment = new Comment()
    comment.text = params.text
    comment.date = new LocalDate()
    comment.time = new LocalTime()
    comment.author = userService.user
    comment.lunch = lunch

    if (comment.save()) {
      redirect action: "show", id: lunch.id
    } else {
      throw new RuntimeException(comment.errors)
    }
  }

  @AuthRequired
  def create = {
    [lunch: new Lunch()]
  }

  @AuthRequired
  def save = {
    def lunch = new Lunch()
    lunch.properties = params
    lunch.creator = userService.user
    lunch.createDateTime = new LocalDateTime()

    if (lunch.save()) {
      redirect action: "show", id: lunch.id
    } else {
      render(view: "create", model: [lunch: lunch])
    }
  }

  private def getFirstLunchId() {
    Lunch.find("from Lunch order by createDateTime").id
  }

  private def getNextLunchId(def currentLunch) {
    def nextLunch = Lunch.find("from Lunch l where l.id > :id", [id: currentLunch.id])
    def nextId = nextLunch ? nextLunch.id : firstLunchId
  }
}
