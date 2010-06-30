package com.wannalunch.converters;

import java.io.StringWriter;
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

    if (callback) {
      def writer = new StringWriter()
      super.render(writer)
      writer.flush()
      
      out.append(callback).append("(")
      out.append(writer.toString())
      out.append(");")
    } else {
      super.render(out)
    }
  }

}
