package com.secured.auth


class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
        "/register"(controller: 'user',action: 'register')
        "/"(controller: 'main', action:'home')
        "/verify"(controller: "main", action: "verify")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
