package com.wannalunch.propertyeditor

import org.joda.time.LocalDate
import org.joda.time.LocalTime

import org.joda.time.LocalDate
import org.joda.time.LocalDate
import org.joda.time.LocalDate

import org.springframework.beans.PropertyEditorRegistrar
import org.springframework.beans.PropertyEditorRegistry

class CustomPropertyEditorRegistrar implements PropertyEditorRegistrar {

  def dateEditor
  def timeEditor

  @Override
  public void registerCustomEditors(PropertyEditorRegistry registry) {
	registry.registerCustomEditor LocalDate, dateEditor
	registry.registerCustomEditor LocalTime, timeEditor
  }

}
