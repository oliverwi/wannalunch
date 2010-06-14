package com.wannalunch.converters;

import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.groovy.grails.web.converters.exceptions.ConverterException;
import org.codehaus.groovy.grails.web.json.JSONException;
import org.springframework.web.context.request.RequestContextHolder;

import grails.converters.JSON;

public class JSONP extends JSON {

  JSONP() {
    super()
  }

  JSONP(target) {
    super(target)
  }

  void render(Writer out) {
    def callback = RequestContextHolder.requestAttributes.params.callback

    if (!callback) {
      throw new IllegalArgumentException("param 'callback' is not present")
    }

    out.append(callback).append("(")
    super.render(out)
    out.append(")")
  }

}
