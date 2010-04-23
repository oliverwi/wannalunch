package com.wannalunch.controllers

import com.wannalunch.domain.Lunch
import com.wannalunch.domain.User

import org.joda.time.LocalDateTime

class LunchController {
  
  static defaultAction = "show"
  
  def show = {
    def id = params.id ?: firstLunchId
    def lunch = Lunch.get(id)
    
    [lunch: lunch, nextId: getNextLunchId(lunch)]
  }
  
  def join = {
    User oliver = User.find("from User where name like :name", [name: "Oliver%"])
    def lunch = Lunch.get(params.id)
    
    lunch.addToParticipants(oliver)
    if (lunch.save()) {
      redirect action: "show", id: lunch.id
    } else {
      throw new RuntimeException("Oops!")
    }
  }
  
  def create = {
    [lunch: new Lunch()]
  }
  
  def save = {
    def lunch = new Lunch()
    lunch.properties = params
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
    def nextLunch = Lunch.find("from Lunch l where l.createDateTime > :createDateTime", [createDateTime: currentLunch.createDateTime])
    def nextId = nextLunch ? nextLunch.id : firstLunchId
  }
}
