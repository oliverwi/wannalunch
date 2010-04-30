package com.wannalunch.aop

@interface Mail {

  MailType value()

}

enum MailType {
  CREATED_LUNCH, ACCEPTED_PARTICIPANT;
}
