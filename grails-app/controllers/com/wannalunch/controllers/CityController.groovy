package com.wannalunch.controllers

import org.codehaus.groovy.grails.commons.ConfigurationHolder;

import com.wannalunch.domain.City;

class CityController {

  def userService
  
  def config = ConfigurationHolder.config
  
  def change = {
    City city = City.get(params.id)
    userService.city = city
    
    redirect controller: "lunch"
  }
}
