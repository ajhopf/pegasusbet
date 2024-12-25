package pegasusdatastore

import exceptions.ResourceNotFoundException

class UrlMappings {

    static mappings = {
        "/horses/$id" (controller: 'horse', action: 'update')

        "/jockeys/$id" (controller: 'jockey', action: 'update')

        "/odds/$id" (controller: 'odds', action: 'getOddsByRaceHorseJockey')

        "/races/$id" (controller: 'race', action: 'getRaceByRaceHorseJockeyId')

        "/odds"(resources: 'odds')
        "/jockeys"(resources: 'jockey')
        "/horses"(resources: 'horse')
        "/racecourses"(resources: 'raceCourse')
        "/races"(resources: 'race')

        "500"(controller: 'errors', action: 'handleResourceNotFoundException', exception: ResourceNotFoundException)

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
