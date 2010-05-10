package com.wannalunch.controllers

import org.codehaus.groovy.grails.commons.ConfigurationHolder;

import com.wannalunch.domain.City;

class CityController extends AbstractController {

  def config = ConfigurationHolder.config
  
  def change = {
    City city = City.get(params.id)
    setCity(city)
    
    String referer = request.getHeader("referer")
    if (referer.contains("create") || referer.contains("lunch/save")) {
      redirect controller: "lunch", action: "create"
    } else {
      redirect controller: "lunch"
    }
  }
}
