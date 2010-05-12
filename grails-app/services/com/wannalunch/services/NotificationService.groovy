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
import com.wannalunch.support.UserLocaleMessageSourceAccessor;

class NotificationService {

  boolean transactional = false

  JavaMailSender mailSender
  
  MailBuilder mailBuilder
  
  UserLocaleMessageSourceAccessor userMessageSource

  String from = ConfigurationHolder.config.mail.from

  String encoding = ConfigurationHolder.config.mail.defaultEncoding
  
  String serverURL = ConfigurationHolder.config.grails.serverURL
  
  boolean sendEmails = ConfigurationHolder.config.mail.sendMails
  
  void sendApplicationNotification(User user, Lunch lunch) {
    if (shouldSendEmailsToCreatorOf(lunch)) {
      User creator = lunch.creator
      sendEmail(
          creator.email,
          userMessageSource.getMessage("mail.application.subject"),
          userMessageSource.getMessage("mail.application.body", [creator.name, user.name, lunch.topic, getLunchURL(lunch)]))
    }
  }
  
  void sendAcceptanceNotification(User user, Lunch lunch) {
    User creator = lunch.creator
    sendEmail(
        user.email,
        userMessageSource.getMessage("mail.acceptance.subject"),
        userMessageSource.getMessage("mail.acceptance.body", [user.name, creator.name, lunch.topic, getLunchURL(lunch)]))
  }
  
  void sendCommentNotification(Comment comment) {
    Lunch lunch = comment.lunch
    User creator = lunch.creator
    User author = comment.author
    
    if (shouldSendEmailsToCreatorOf(comment.lunch) && comment.author != comment.lunch.creator) {
      sendEmail(
          creator.email, 
          userMessageSource.getMessage("mail.comment.subject"),
          userMessageSource.getMessage("mail.comment.body", [creator.name, author.name, lunch.topic, comment.text, getLunchURL(lunch)]))
    }
    
    // TODO also send notification to applicants and participants
  }
  
  private boolean shouldSendEmailsToCreatorOf(Lunch lunch) {
    return lunch.creatorWantsNotifications && lunch.creator.email
  }
  
  private String getLunchURL(Lunch lunch) {
    return serverURL + "/lunch/show/$lunch.id"
  }
  
  private void sendEmail(String to, String subject, String body) {
    if (sendEmails) {
      def message = mailBuilder
          .createMail()
          .from(from)
          .to(to)
          .withSubject(subject)
          .withBody(body + userMessageSource.getMessage("mail.default.footer"))
          .done()
      
      Thread.start {
        log.info "Sending e-mail to $to"
        mailSender.send(message)
      }
    }
  }
}
