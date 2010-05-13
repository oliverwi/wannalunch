package com.wannalunch.controllers

import java.net.URL;

import com.wannalunch.domain.City;
import com.wannalunch.domain.User;

abstract class AbstractController {

  def userService

  boolean isLoggedIn() {
    userService.isLoggedIn()
  }

  User getLoggedInUser() {
    isLoggedIn() ? userService.getUser() : null
  }

  void setCity(City city) {
    userService.city = city
  }

  City getCity() {
    if (!userService.city) {
      userService.city = detectUserLocation()
    }
    userService.city
  }

  private City detectUserLocation() {
    def ip = request.remoteAddr
    def conn = new URL("http://ipinfodb.com/ip_query.php?ip=${ip}&timezone=false").openConnection()
    conn.doOutput = true

    def writer = new OutputStreamWriter(conn.outputStream)
    writer.flush()

    def detectedCity = getCityFromXMLResponse(conn.inputStream.text)

    return detectedCity ?: City.findByName("World")
  }

  private City getCityFromXMLResponse(String xmlResponse) {
    def matcher = xmlResponse =~ /<City>(.+)<\/City>/
    if (matcher.find() && matcher[0].size() > 1) {
      return City.findByName(matcher[0][1])
    }
    return null
  }
}
