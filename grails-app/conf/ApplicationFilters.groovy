import org.codehaus.groovy.grails.commons.ApplicationHolder;
import com.wannalunch.aop.AuthRequired;

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
          redirect controller: controllerName
          return false
        }

        return true
      }
    }
  }

  private static def getActionsRequiringAuthentication() {
    def requireAuthentication = [:]

    ApplicationHolder.application.getArtefacts("Controller").each { controllerClass ->
      controllerClass.metaClass.properties.each { prop ->
        def cachedField = prop.field
        if (cachedField && cachedField.field.isAnnotationPresent(AuthRequired)) {
          def controllerClassName = controllerClass.logicalPropertyName
          if (!requireAuthentication[controllerClassName]) {
            requireAuthentication[controllerClassName] = []
          }
          requireAuthentication[controllerClassName].push(prop.name)
        }
      }
    }

    requireAuthentication
  }

}
