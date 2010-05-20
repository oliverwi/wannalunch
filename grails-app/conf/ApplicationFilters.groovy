import org.codehaus.groovy.grails.commons.ApplicationHolder;
import com.wannalunch.aop.AuthRequired;
import com.wannalunch.domain.City;

class ApplicationFilters {

  def filters = {
    checkAuth(controller: '*', action: '*') {
      def requireAuthentication = getActionsRequiringAuthentication()

      before = {
        if (!controllerName) {
          return true
        }

        def userService = applicationContext.userService
        if (actionName in requireAuthentication[controllerName] && !userService.loggedIn) {
          flash.message = "You must be logged in"
          redirect uri: "/"
          return false
        }

        return true
      }
    }

    initCity(controller: '*', action: '*') {
      before = {
        applicationContext.userService.city = detectUserLocation(request)
      }
    }
  }

  private static def getActionsRequiringAuthentication() {
    def requireAuthentication = [:]

    ApplicationHolder.application.getArtefacts("Controller").each { controllerClass ->
      def controllerClassName = controllerClass.logicalPropertyName
      requireAuthentication[controllerClassName] = []

      def allActionsRequireAuth = controllerClass.clazz.isAnnotationPresent(AuthRequired)

      controllerClass.URIs.each { uri ->
        def action = controllerClass.getClosurePropertyName(uri)
        def prop = controllerClass.metaClass.getMetaProperty(action)

        if (prop) {
          def cachedField = prop.field
          if (allActionsRequireAuth || (cachedField && cachedField.field.isAnnotationPresent(AuthRequired))) {
            requireAuthentication[controllerClassName].push(prop.name)
          }
        }
      }
    }

    requireAuthentication
  }

  private static City detectUserLocation(request) {
    def ip = request.remoteAddr
    def conn = new URL("http://ipinfodb.com/ip_query.php?ip=${ip}&timezone=false").openConnection()
    conn.doOutput = true

    def writer = new OutputStreamWriter(conn.outputStream)
    writer.flush()

    def detectedCity = getCityFromXMLResponse(conn.inputStream.text)

    return detectedCity ?: City.findByName("World")
  }

  private static City getCityFromXMLResponse(String xmlResponse) {
    def matcher = xmlResponse =~ /<City>(.+)<\/City>/
    if (matcher.find() && matcher[0].size() > 1) {
      return City.findByName(matcher[0][1])
    }
    return null
  }

}
