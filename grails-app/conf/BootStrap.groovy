import grails.util.Environment;

import java.util.Random;


import org.joda.time.LocalDateTime
import org.joda.time.LocalDate
import org.joda.time.LocalTime

import com.wannalunch.domain.City;
import com.wannalunch.domain.Comment;
import com.wannalunch.domain.Lunch
import com.wannalunch.domain.TwitterAccount
import com.wannalunch.domain.User;

class BootStrap {

  def init = { servletContext ->
    if (Environment.current.name != "test") {
      addCitiesIfNeeded()
      moveAllCitylessLunchesToTallinn()
    }

    if (Environment.current.name in ["development", "timur"]) {
      addFakeData()
    }
  }

  private void addCitiesIfNeeded() {
    def createCityIfNeeded = { cityName ->
      if (!City.findByName(cityName)) {
        City city = new City(name: cityName)
        assert city.save(flush: true), "Could not save city with name $cityName"
      }
    }

    createCityIfNeeded("Tallinn")
    createCityIfNeeded("Tartu")
    createCityIfNeeded("World")
  }

  private void moveAllCitylessLunchesToTallinn() {
    Lunch.findAll("from Lunch l where l.city is null").each{
      it.city = City.findByName("Tallinn")
      assert it.save(), "Could not move lunch with it ${it.id} to Tallinn: ${it.errors}"
    }
  }

  private void addFakeData() {
    def username = "timurstrekalov"

    User timur = new User()
    timur.name = "Timur Strekalov"
    timur.username = username
    timur.twitterAccount = new TwitterAccount(username: username)
    timur.email = "timur.strekalov@gmail.com"
    timur.profileImageUrl = "http://a1.twimg.com/profile_images/755799796/userpic.jpeg"

    username = "oliverwi"

    User oliver = new User()
    oliver.name = "Oliver Wihler"
    oliver.email = "luiz.ribeiro@aqris.com"
    oliver.username = username
    oliver.twitterAccount = new TwitterAccount(username: username)
    oliver.profileImageUrl = "http://a1.twimg.com/profile_images/300575924/ls_6914_2009-04-09_at_21-37-24__1_.jpg"

    assert timur.save(), timur.errors
    assert oliver.save(), oliver.errors

    City tallinn = City.findByName("Tallinn")

    20.times {
      def lunch = createLunch(timur, oliver, tallinn)
      assert lunch.save(), lunch.errors
    }

    def todaysLunch = new Lunch()
    todaysLunch.creator = timur
    todaysLunch.createDateTime = new LocalDateTime()
    todaysLunch.topic = "Today's lunch"
    todaysLunch.description = "Who cares?"
    todaysLunch.date = new LocalDate()
    todaysLunch.time = new LocalTime(12, 0)
    todaysLunch.location = "Vapiano"
    todaysLunch.city = tallinn
    todaysLunch.addToParticipants(oliver)
    assert todaysLunch.save(), todaysLunch.errors
  }

  private Lunch createLunch(user1, user2, city) {
    def random = new Random()

    Lunch lunch = new Lunch()
    User lunchCreator = random.nextInt(100) % 2 > 0 ? user1 : user2
    lunch.creator = lunchCreator
    lunch.creatorWantsNotifications = true
    lunch.topic = "Let's talk about that topic number ${random.nextInt(9999) + 1}"
    lunch.description = "am attending http://thenextweb.com/conference/ in Amsterdam April 27+28+29 and would love to share my experiences to others who have attended or with anyone who has an interest in the future of the interwebz :) "
    lunch.createDateTime = new LocalDateTime().minusHours(random.nextInt(168) + 1)
    lunch.date = new LocalDate().plusDays(random.nextInt(30))
    lunch.time = new LocalTime().plusHours(random.nextInt(24) - 12)
    lunch.location = ["Vapiano", "Silk", "Cafe Bonaparte", "Fahle", "Sushi Cat", "Cafe Tao"].get(random.nextInt(5))
    lunch.city = city
    if (random.nextInt(100) % 2 > 0) {
      User applicant = (lunch.creator == user1) ? user2 : user1
      lunch.addToApplicants(applicant)
      if (random.nextInt(100) % 2 > 0) {
        Comment comment = new Comment()
        comment.text = "I am interested"
        comment.author = lunch.creator == user1 ? user2 : user1
        comment.date = new LocalDate()
        comment.time = new LocalTime()
        comment.lunch = lunch
      }
    }

    return lunch
  }

  def destroy = {
  }
}
