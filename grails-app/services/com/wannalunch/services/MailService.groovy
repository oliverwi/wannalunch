package com.wannalunch.services

import com.wannalunch.aop.MailType;
import java.util.Map;
import javax.mail.internet.MimeMessage
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.springframework.core.io.ByteArrayResource
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service;

class MailService {

  boolean transactional = false

  def mailSender

  def mailCfg = ConfigurationHolder.config.mail

  String encoding = mailCfg.defaultEncoding
  String senderEmail = mailCfg.from

  void sendMail(MailType emailType, Map params) {
    switch (emailType) {
      case MailType.CREATED_LUNCH:
        break;
      case MailType.ACCEPTED_PARTICIPANT:
        break;
      default:
        throw new IllegalArgumentException("Unknown email type: " + emailType)
    }
  }

  void maybeSendMail() {

  }

  private void sendMail(Map params) {
    if (!mailCfg.sendMails) {
      log.debug "Not sending email $params"
      return
    }

    MimeMessage message = mailSender.createMimeMessage()
    MimeMessageHelper helper = new MimeMessageHelper(message, true, encoding)

    helper.setFrom(params.from ?: senderEmail)
    helper.setTo(params.to)
    helper.setSubject(params.subject)
    helper.setText(params.text, false)

    if (params.document) {
      helper.addAttachment(params.document.fileName, new ByteArrayResource(params.document.content.data))
    }

    try {
      mailSender.send(message);
    } catch (Exception e) {
      log.warn "Supressed error in sending e-mail to '${params.to}': $e.message"
    }
  }

}
