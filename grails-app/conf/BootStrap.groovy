import grails.util.Environment;

import java.util.Random;

import com.wannalunch.aop.AuthRequired;

import org.codehaus.groovy.grails.commons.ApplicationHolder
import org.joda.time.LocalDateTime
import org.joda.time.LocalDate
import org.joda.time.LocalTime

import com.wannalunch.domain.Comment;
import com.wannalunch.domain.Lunch
import com.wannalunch.domain.User;
import com.wannalunch.support.DataMigrator;

class BootStrap {

  def init = { servletContext ->
    if (Environment.current.name in ["development"]) {
      addFakeData()
    }
  }

  private void addFakeData() {
    User timur = new User()
    timur.name = "Timur Strekalov"
    timur.username = "timurstrekalov"
    timur.email = "timur.strekalov@gmail.com"
    timur.profileImageUrl = "http://a1.twimg.com/profile_images/755799796/userpic.jpeg"

    User oliver = new User()
    oliver.name = "Oliver Wihler"
    oliver.email = "blabla@blabla.com"
    oliver.username = "oliverwi"
    oliver.profileImageUrl = "http://a1.twimg.com/profile_images/300575924/ls_6914_2009-04-09_at_21-37-24__1_.jpg"

    assert timur.save(), timur.errors
    assert oliver.save(), oliver.errors

    20.times {
      createLunch(timur, oliver).save()
    }
  }

  private Lunch createLunch(user1, user2) {
    def random = new Random()

    Lunch lunch = new Lunch()
    lunch.creator = random.nextInt(100) % 2 > 0 ? user1 : user2
    lunch.topic = "Let's talk about that topic number ${random.nextInt(9999) + 1}"
    lunch.description = "am attending http://thenextweb.com/conference/ in Amsterdam April 27+28+29 and would love to share my experiences to others who have attended or with anyone who has an interest in the future of the interwebz :) "
    lunch.createDateTime = new LocalDateTime().minusHours(random.nextInt(168) + 1)
    lunch.date = new LocalDate().plusDays(random.nextInt(30))
    lunch.time = new LocalTime().plusHours(random.nextInt(24) - 12)
    lunch.location = ["Vapiano", "Silk", "Cafe Bonaparte", "Fahle", "Sushi Cat", "Cafe Tao"].get(random.nextInt(5))
    if (random.nextInt(100) % 2 > 0) {
      lunch.addToApplicants(lunch.creator == user1 ? user2 : user1)
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
