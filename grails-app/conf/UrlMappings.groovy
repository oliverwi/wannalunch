import grails.util.Environment;

class UrlMappings {

  static mappings = {
    "/$controller/$action?/$id?" {
      constraints {
        // apply constraints here
      }
    }
    "/"(controller: "lunch")

    def errorPage = Environment.isDevelopmentMode() ? "/error" : "/errorProduction"

    "403"(view: errorPage)
    "404"(view: errorPage)
    "500"(view: errorPage)

    "/about"(uri: "/about/index.gsp")
    "/about/partners"(uri: "/about/partners.gsp")
    "/about/signup"(uri: "/about/signup.gsp")
  }

}
