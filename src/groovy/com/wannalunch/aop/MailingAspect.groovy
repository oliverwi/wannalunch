package com.wannalunch.aop

import com.wannalunch.domain.Lunch;
import com.wannalunch.domain.Comment;
import com.wannalunch.domain.User;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
class MailingAspect {

  def mailService
  def mailBuilder

  @Pointcut("@annotation(com.wannalunch.aop.Mail)")
  void lunchOperation() {}

  @AfterReturning(pointcut = "com.wannalunch.aop.MailingAspect.lunchOperation() && @annotation(mail)",
      returning = "comment")
  void mail(Mail mail, Comment comment) {
    def lunch = comment.lunch
    def creator = lunch.creator
    def author = comment.author

    if (creator.username == author.username) {
      return
    }

    def text = comment.text

    mailService.sendMail(creator, mail.value(), [author.name, lunch.topic, text, lunch.showUrl])
  }

  @AfterReturning(pointcut = "com.wannalunch.aop.MailingAspect.lunchOperation() && @annotation(mail) && args(applicant, lunch)")
  void mail(Mail mail, User applicant, Lunch lunch) {
    mailService.sendMail(applicant, mail.value(), [lunch.creator.name, lunch.topic, lunch.showUrl])
  }

}
