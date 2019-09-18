package com.secured.main

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        post "/company"(controller: "apiCompanyEndpoints", action: "save")
        put "/company"(controller: "apiCompanyEndpoints", action: "update")

        post "/barcode"(controller: "apiBarCodeEndpoints", action: "save")
        put "/barcode"(controller: "apiBarCodeEndpoints", action: "delete")

        "/register"(controller: 'register',action: 'register')
        "/register/confirm"(controller: 'register', action: "confirm")
        "/"(controller: 'main', action:'home')
        "/verify"(controller: "main", action: "verify")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
