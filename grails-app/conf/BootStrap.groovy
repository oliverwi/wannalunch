import org.joda.time.LocalDateTime
import org.joda.time.LocalDate
import org.joda.time.LocalTime

import com.wannalunch.domain.Comment;
import com.wannalunch.domain.Lunch
import com.wannalunch.domain.User;

class BootStrap {

  def init = { servletContext ->
    User timur = new User()
    timur.name = "Timur Strekalov"
    timur.username = "timurstrekalov"
    timur.profileImageUrl = "http://a1.twimg.com/profile_images/755799796/userpic.jpeg"

    User oliver = new User()
    oliver.name = "Oliver Wihler"
    oliver.username = "oliverwi"
    oliver.profileImageUrl = "http://a1.twimg.com/profile_images/300575924/ls_6914_2009-04-09_at_21-37-24__1_.jpg"

    Lunch lunch1 = new Lunch()
    lunch1.creator = oliver
    lunch1.topic = "Topic 1"
    lunch1.description = "Talking about something"
    lunch1.createDateTime = new LocalDateTime().minusHours(1)
    lunch1.date = new LocalDate().plusDays(1)
    lunch1.time = new LocalTime()
    lunch1.location = "Vapiano"
    lunch1.addToApplicants(timur)

    Lunch lunch2 = new Lunch()
    lunch2.creator = oliver
    lunch2.topic = "Topic 2"
    lunch2.description = "Who's interested to talk about another thing?"
    lunch2.createDateTime = new LocalDateTime()
    lunch2.date = new LocalDate().plusDays(2)
    lunch2.time = new LocalTime()
    lunch2.location = "Vapiano"

    Lunch lunch3 = new Lunch()
    lunch3.creator = timur
    lunch3.topic = "Let's talk about how awesome I am!"
    lunch3.description = "Who could ever be interested in talking about another thing?"
    lunch3.createDateTime = new LocalDateTime()
    lunch3.date = new LocalDate().plusDays(3)
    lunch3.time = new LocalTime()
    lunch3.location = "Galerii kohvik"

    Comment comment1 = new Comment()
    comment1.text = "I am interested"
    comment1.author = timur
    comment1.date = new LocalDate()
    comment1.time = new LocalTime()
    comment1.lunch = lunch1

    assert timur.save(), timur.errors
    assert oliver.save(), oliver.errors
    lunch1.save()
    lunch2.save()
    lunch3.save()
    comment1.save()
  }

  def destroy = {
  }
}
