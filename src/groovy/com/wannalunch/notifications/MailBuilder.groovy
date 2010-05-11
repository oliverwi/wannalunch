package com.wannalunch.notifications

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

class MailBuilder {
  
  JavaMailSender mailSender

  MimeMessageHelper helper
  
  MimeMessage message
  
  def userMessageSource
  
  MailBuilder(JavaMailSender mailSender) {
    this.mailSender = mailSender
  }
  
  MailBuilder createMail() {
    message = mailSender.createMimeMessage()
    helper = new MimeMessageHelper(message, true)
    return this
  }
  
  MailBuilder from(String from) {
    helper.setFrom(from)
    return this
  }
  
  MailBuilder to(String to) {
    helper.setTo(to)
    return this
  }
  
  MailBuilder withSubject(String subject) {
    helper.subject = subject
    return this
  }

  MailBuilder withBody(String text) {
    helper.text = text
    return this
  }
  
  MimeMessage done() {
    return message
  }
}
