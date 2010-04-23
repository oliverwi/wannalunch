package com.wannalunch.propertyeditor

import com.wannalunch.support.UserLocaleMessageSourceAccessor
import java.beans.PropertyEditorSupport;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter

class LocalTimeEditor extends PropertyEditorSupport {

  DateTimeFormatter formatter

  LocalTimeEditor() { }

  LocalTimeEditor(userMessageSource) {
    formatter = DateTimeFormat.forPattern(userMessageSource.getMessage('default.time.format'))
  }

  @Override
  public void setAsText(final String text) throws IllegalArgumentException {
    if (text != "") {
      setValue(formatter.parseDateTime(text).toLocalTime())
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
