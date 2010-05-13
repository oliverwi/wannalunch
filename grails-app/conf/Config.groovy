grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination

grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false

grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
    xml: ['text/xml', 'application/xml'],
    text: 'text/plain',
    js: 'text/javascript',
    rss: 'application/rss+xml',
    atom: 'application/atom+xml',
    css: 'text/css',
    csv: 'text/csv',
    all: '*/*',
    json: ['application/json','text/json'],
    form: 'application/x-www-form-urlencoded',
    multipartForm: 'multipart/form-data'
    ]

// The default codec used to encode data with ${}
grails.views.default.codec = "html" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable fo AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []

// set per-environment serverURL stem for creating absolute links
environments {
  production {
    grails.config.locations = ["classpath:ProductionConfig.groovy"]
    authController = "oauth"
  }

  development {
    grails.serverURL = "http://localhost:8080/${appName}"
    authController = "fakeauth"

    twitter.sendTweets = false
    mail.sendMails = false

    mail.host = "smtp.sendgrid.net"
    mail.username = "lunch@wannalunch.com"
    mail.password = "bazzinga"
    mail.port = 465
    mail.protocol = "smtps"
    mail.from = "lunch@wannalunch.com"
    mail.defaultEncoding = "UTF-8"
  }

  test {
    grails.serverURL = "http://localhost:8080/${appName}"
    twitter.authController = "fakeauth"
    twitter.sendTweets = false
    mail.sendMails = false
  }

  luiz {
    grails.serverURL = "http://localhost:8080/${appName}"
    authController = "fakeauth"
    twitter.sendTweets = false
    mail.sendMails = false
    mail.host = ""
    mail.from = ""
    mail.defaultEncoding = "UTF-8"
    grails.gsp.enable.reload = true
  }

  timur {
//    grails.serverURL = "https://aqris.com/ams-timur"
//    authController = "oauth"
    grails.serverURL = "http://localhost:8080/${appName}"
    authController = "fakeauth"

    twitter.oauth.consumerKey = 'HQCgtBoVwFpnAnJO2DwQcA'
    twitter.oauth.consumerSecret = 'w1DhISNrommAU6ZDFhbW4y8mvpFqWZV1ddgtNGxHYs0'

    facebook.oauth.apiKey = "63ed680b9eb59465ad0b9f5c100ccef3"
    facebook.oauth.apiSecret = "7e513d208acc8c8cfca2d5836c492183"

    twitter.sendTweets = false
    mail.sendMails = false

    mail.host = "smtp.sendgrid.net"
    mail.username = "lunch@wannalunch.com"
    mail.password = "bazzinga"
    mail.port = 465
    mail.protocol = "smtps"
    mail.from = "lunch@wannalunch.com"
    mail.defaultEncoding = "UTF-8"
  }
}

// log4j configuration
log4j = {
  error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
         'org.codehaus.groovy.grails.web.pages', //  GSP
         'org.codehaus.groovy.grails.web.sitemesh', //  layouts
         'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
         'org.codehaus.groovy.grails.web.mapping', // URL mapping
         'org.codehaus.groovy.grails.commons', // core / classloading
         'org.codehaus.groovy.grails.plugins', // plugins
         'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
         'org.springframework',
         'org.hibernate',
         'net.sf.ehcache.hibernate'
//  info   'org.hibernate'
//  trace  'org.hibernate.SQL',
//         'org.hibernate.type'

 debug   'com.wannalunch',
         'grails.app'

  warn   'org.mortbay.log'
}
