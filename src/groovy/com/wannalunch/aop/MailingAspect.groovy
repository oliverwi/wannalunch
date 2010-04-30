package com.wannalunch.aop

import com.wannalunch.domain.Lunch;
import com.wannalunch.domain.User;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Aspect
class MailingAspect implements ApplicationContextAware {

  ApplicationContext applicationContext

  @Pointcut("@annotation(com.wannalunch.aop.Email)")
  void lunchOperation() {}

  @AfterReturning(pointcut = "com.wannalunch.aop.EmailingAspect.lunchOperation() && @annotation(email) && args(lunch)")
  void mail(Mail email, Lunch lunch) {
//    mailService.sendMail(email.value(), [lunch: lunch])
  }

  @AfterReturning(pointcut = "com.wannalunch.aop.EmailingAspect.lunchOperation() && @annotation(email) && args(user, lunch)")
  void mail(Mail email, User user, Lunch lunch) {
//    mailService.sendMail(email.value(), [user: user, lunch: lunch])
  }

  private def getMailService() {
    applicationContext.mailService
  }

}
