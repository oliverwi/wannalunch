import org.joda.time.LocalDateTime
import org.joda.time.LocalDate
import org.joda.time.LocalTime

import com.wannalunch.domain.Lunch

class BootStrap {

  def init = { servletContext ->
    Lunch lunch1 = new Lunch()
    lunch1.topic = "Topic 1"
    lunch1.description = "Desc 1"
    lunch1.createDateTime = new LocalDateTime().minusHours(1)
    lunch1.date = new LocalDate()
    lunch1.time = new LocalTime()
    lunch1.place = "Vapiano"
    	
    Lunch lunch2 = new Lunch()
    lunch2.topic = "Topic 2"
    lunch2.description = "Desc 2"
    lunch2.createDateTime = new LocalDateTime()
    lunch2.date = new LocalDate()
    lunch2.time = new LocalTime()
    lunch2.place = "Vapiano"
    
    lunch1.save()
    lunch2.save()
  }

  def destroy = {
  }
} 
