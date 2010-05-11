package com.wannalunch.services

import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.wannalunch.notifications.MailBuilder;

import grails.test.GrailsUnitTestCase;

class MailTest extends GrailsUnitTestCase {

  void testMailBuilder() {
    MailBuilder builder = new MailBuilder(new JavaMailSenderImpl())
    MimeMessage message = builder
                         .createMail()
                         .from("lunch@wannalunch.com")
                         .to("timur@aqris.com")
                         .withSubject("Subject")
                         .withBody("Text")
                         .done()
    
    assertEquals("lunch@wannalunch.com", message.from[0].address)
    assertEquals("timur@aqris.com", message.allRecipients[0].address)
    assertEquals("Subject", message.subject)
  }
  
  void testSendEmail() {
//    JavaMailSenderImpl mailSender = new JavaMailSenderImpl()
//    
//    Properties properties = new Properties()
//    properties.put "mail.smtp.auth", "true"
//    properties.put "mail.smtp.starttls.enable", "true"
//    properties.put "mail.smtp.starttls.required", "true"
//    
//    mailSender.setJavaMailProperties(properties)
//    mailSender.setProtocol("smtps")
//    mailSender.setHost("smtp.sendgrid.net")
//    mailSender.setUsername("lunch@wannalunch.com")
//    mailSender.setPassword("bazzinga")
//    mailSender.setPort(465)
//    
//    MimeMessage message = mailSender.createMimeMessage()
//    MimeMessageHelper helper = new MimeMessageHelper(message, true)
//
//    helper.setFrom("lunch@wannalunch.com")
//    helper.setTo("timur@aqris.com")
//    helper.setSubject("BAZZINGA!")
//    helper.setText("It works =]")
//    
//    mailSender.send(message)
  }
}
