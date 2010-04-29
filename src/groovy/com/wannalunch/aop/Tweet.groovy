package com.wannalunch.aop

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface Tweet {

  TweetType value()

}

enum TweetType {
  LUNCH_WITH_ME, LUNCH_WITH_YOU, LUNCH_WITH_EACH_OTHER;
}