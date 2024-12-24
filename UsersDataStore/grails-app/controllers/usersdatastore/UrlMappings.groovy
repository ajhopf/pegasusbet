package usersdatastore

class UrlMappings {

    static mappings = {
        "/user" (controller: 'user', action: 'save')
        "/user/admin" (controller: 'user', action: 'createAdmin')

        "/wallet/$username" (controller: "wallet", action: "getWalletInfo")
        "/wallet/transaction/$username" (controller: "wallet", action: "addTransaction")

        delete "/$controller/$id(.$format)?"(action:"delete")
        get "/$controller(.$format)?"(action:"index")
        get "/$controller/$id(.$format)?"(action:"show")
        post "/$controller(.$format)?"(action:"save")
        put "/$controller/$id(.$format)?"(action:"update")
        patch "/$controller/$id(.$format)?"(action:"patch")

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }
}
