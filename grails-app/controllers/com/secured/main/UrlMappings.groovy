package com.secured.main


class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
        "/api"(controller: "apiTest")
        "/register"(controller: 'register',action: 'register')
        "/register/confirm"(controller: 'register', action: "confirm")
        "/"(controller: 'main', action:'home')
        "/verify"(controller: "main", action: "verify")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
