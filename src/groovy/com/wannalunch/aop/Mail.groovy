package com.wannalunch.aop

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Mail {

  Kind value()

  enum Kind {
    ACCEPT,
    COMMENT,
    REMINDER;
  }

}
