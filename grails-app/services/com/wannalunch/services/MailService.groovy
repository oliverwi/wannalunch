package com.wannalunch.services

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

  def encoding = mailCfg.defaultEncoding
  def from = mailCfg.from

  private void sendMail(Map params) {
    if (!mailCfg.sendMails) {
      log.debug "Not sending email $params"
      return
    }

    MimeMessage message = mailSender.createMimeMessage()
    MimeMessageHelper helper = new MimeMessageHelper(message, true, encoding)

    helper.setFrom(params.from ?: from)
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
