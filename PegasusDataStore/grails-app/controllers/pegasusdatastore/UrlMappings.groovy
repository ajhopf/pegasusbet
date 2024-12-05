package pegasusdatastore

class UrlMappings {

    static mappings = {
        "/jockeys"(resources: 'jockey')
        "/horses"(resources: 'horse')
        "/racecourses"(resources: 'raceCourse')

        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
