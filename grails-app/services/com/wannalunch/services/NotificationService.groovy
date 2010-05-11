package com.wannalunch.services

import java.util.List;
import java.util.Map;
import javax.mail.internet.MimeMessage

import org.codehaus.groovy.grails.commons.ConfigurationHolder

import org.springframework.core.io.ByteArrayResource
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper

import com.wannalunch.domain.Comment;
import com.wannalunch.domain.Lunch;
import com.wannalunch.domain.User;
import com.wannalunch.notifications.MailBuilder;

class NotificationService {

  boolean transactional = false

  JavaMailSender mailSender
  
  MailBuilder mailBuilder

  String from = ConfigurationHolder.config.mail.from

  String encoding = ConfigurationHolder.config.mail.defaultEncoding
  
  void sendCommentNotification(Comment comment) {
    if (shouldSendEmailsToCreatorOf(comment.lunch) && comment.author != comment.lunch.creator) {
      sendEmail(
          comment.lunch.creatorEmail, 
          "Wannalunch: ${comment.author.name} commented on your lunch",
          "${comment.author.name} wrote on ${comment.date} - ${comment.time}: \n ${comment.text}")
    }
  }
  
  void sendApplicationNotification(User user, Lunch lunch) {
    if (shouldSendEmailsToCreatorOf(lunch)) {
      sendEmail(
          comment.lunch.creatorEmail,
          "Wannalunch: ${user.name} applied to your lunch",
          "${user.name} applied to your lunch ${lunch.topic}")
    }
  }
  
  void sendAcceptanceNotification(User user, Lunch lunch) {
    sendEmail(
        user.email,
        "Wannalunch: ${lunch.creator.name} accepted you to his lunch",
        "${lunch.creator.name} accepted you to participate on his/her lunch ${lunch.topic}")
  }
  
  private boolean shouldSendEmailsToCreatorOf(Lunch lunch) {
    return lunch.creatorWantsNotifications && lunch.creatorEmail
  }
  
  private void sendEmail(String to, String subject, String body) {
    def message = mailBuilder
        .createMail()
        .from(from)
        .to(to)
        .withSubject(subject)
        .withBody(body)
        .done()
    
    mailSender.send(message)
  }
}
