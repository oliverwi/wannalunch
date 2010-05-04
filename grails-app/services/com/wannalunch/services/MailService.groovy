package com.wannalunch.services

import java.util.List;
import java.util.Map;
import javax.mail.internet.MimeMessage

import org.codehaus.groovy.grails.commons.ConfigurationHolder

import org.springframework.core.io.ByteArrayResource
import org.springframework.mail.javamail.MimeMessageHelper

import com.wannalunch.aop.Mail.Kind;
import com.wannalunch.domain.Luncher;

class MailService {

  boolean transactional = false

  def mailSender
  def mailBuilder

  def mailCfg = ConfigurationHolder.config.mail

  def encoding = mailCfg.defaultEncoding
  def from = mailCfg.from

  void sendMail(Luncher luncher, Kind kind, List args) {
    if (!luncher.wantsNotification) {
      return
    }

    def subject = mailBuilder.subject(kind)
    def body = mailBuilder.body(kind, [luncher.name, args].flatten())
    sendMail(to: luncher.email, subject: subject, text: body)
  }

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
