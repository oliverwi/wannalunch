class UrlMappings {

  static mappings = {
    "/$controller/$action?/$id?" {
      constraints {
        // apply constraints here
      }
    }
    "/"(controller: "lunch")
    "500"(view: "/error")

    "/about"(uri: "/about/index.gsp")
    "/about/partners"(uri: "/about/partners.gsp")
  }

}
