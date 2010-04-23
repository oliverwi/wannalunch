package com.wannalunch.propertyeditor

import com.wannalunch.support.UserLocaleMessageSourceAccessor

import java.beans.PropertyEditorSupport;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter

class LocalDateEditor extends PropertyEditorSupport {

  DateTimeFormatter formatter

  LocalDateEditor() { }

  LocalDateEditor(userMessageSource) {
    formatter = DateTimeFormat.forPattern(userMessageSource.getMessage('default.date.format'))
  }

  @Override
  public void setAsText(final String text) throws IllegalArgumentException {
    if (text != "") {
      setValue(formatter.parseDateTime(text).toLocalDate())
    }
  }

  @Override
  public String getAsText() {
    if (value) {
      return formatter.print(value)
    }

    ''
  }

}
