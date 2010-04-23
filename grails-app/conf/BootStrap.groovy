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
    
    User oliver = new User()
    oliver.name = "Oliver Whiler"
    oliver.username = "oliverwi"
    
    Lunch lunch1 = new Lunch()
    lunch1.creator = oliver
    lunch1.topic = "Topic 1"
    lunch1.description = "Talking about something"
    lunch1.createDateTime = new LocalDateTime().minusHours(1)
    lunch1.date = new LocalDate().plusDays(1)
    lunch1.time = new LocalTime()
    lunch1.location = "Vapiano"
    	
    Lunch lunch2 = new Lunch()
    lunch2.creator = oliver
    lunch2.topic = "Topic 2"
    lunch2.description = "Who's interested to talk about another thing?"
    lunch2.createDateTime = new LocalDateTime()
    lunch2.date = new LocalDate().plusDays(2)
    lunch2.time = new LocalTime()
    lunch2.location = "Vapiano"
      
    Comment comment1 = new Comment()
    comment1.text = "I am interested"
    comment1.author = timur
    comment1.date = new LocalDate()
    comment1.time = new LocalTime()
    comment1.lunch = lunch1
    
    timur.save()
    oliver.save()
    lunch1.save()
    lunch2.save()
    comment1.save()
  }

  def destroy = {
  }
} 
