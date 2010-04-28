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
          redirect uri: "/"
          return false
        }

        return true
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

}
