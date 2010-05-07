package com.wannalunch.test

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import com.wannalunch.domain.City;
import com.wannalunch.domain.Lunch;
import com.wannalunch.domain.User;

class TestDataCreator {

  private static int usersCount = 0
  
  private static int lunchesCount = 0
  
  private static int citiesCount = 0
  
  static User createUser() {
    User user = new User()
    user.name = "User $usersCount"
    user.username = "user$usersCount"
    user.email = "user$usersCount@wannalunch.com"
    user.profileImageUrl = "http://wannalunch.com/img/user$usersCount"
    
    usersCount++
    
    return user
  }
  
  static Lunch createLunch(params) {
    Lunch lunch = new Lunch()
    lunch.topic = "Lunch $lunchesCount"
    lunch.description = "Lunch $lunchesCount's description"
    lunch.createDateTime = new LocalDateTime()
    lunch.date = params.date ?: new LocalDate()
    lunch.time = params.time ?: new LocalTime()
    lunch.location = params.location ?: "Sushi Cat"
    lunch.creator = params.creator ?: createUser()
    lunch.city = params.city ?: createCity()
    
    lunchesCount++
    
    return lunch
  }
  
  static City createCity(params) {
    City city = new City()
    city.name = params?.name ?: "City $citiesCount"
    
    citiesCount++
    
    return city
  }
}
