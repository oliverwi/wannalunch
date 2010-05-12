grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir	= "target/test-reports"
//grails.project.war.file = "target/${appName}-${appVersion}.war"
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits( "global" ) {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsPlugins()
        grailsHome()
        mavenCentral()

        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        //mavenLocal()
        //mavenRepo "http://snapshots.repository.codehaus.org"
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        // specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.

    	compile 'joda-time:joda-time:1.6'
    	compile([group: 'joda-time', name: 'joda-time-hibernate', version: '1.1', transitive: 'false'])
    	compile 'net.homeip.yusuke:twitter4j:2.0.10'
    	compile 'javax.mail:mail:1.4.1'
    	compile 'quartz:quartz:1.5.2'
    	compile 'com.facebook.api:facebook-java-api:2.0.0'
    	runtime 'postgresql:postgresql:8.4-701.jdbc4'
    }

}

grails.war.resources = { stagingDir ->
  // Remove unnecessary classes in production
  if (Environment.current == Environment.PRODUCTION) {
    delete {
      fileset dir: "$stagingDir/WEB-INF/classes/com/wannalunch/controllers", includes: "FakeauthController*.class"
    }
  }
}
