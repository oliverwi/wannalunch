package com.wannalunch.notifications

import com.wannalunch.aop.Mail.Kind;

class MailBuilder {

  def userMessageSource

  def subject(Kind kind) {
    userMessageSource.getMessage("mail.${kind.name()}.subject")
  }

  def body(Kind kind, Object... args) {
    def text = userMessageSource.getMessage("mail.${kind.name()}.body", args)
    def footer = userMessageSource.getMessage("mail.default.footer")
    "$text\n\n$footer"
  }

}
