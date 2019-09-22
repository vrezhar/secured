package com.secured

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
        post "/barcode/$id"(controller: "apiBarCodeEndpoints", action: "update")
        put "/barcode/$id"(controller: "apiBarCodeEndpoints", action: "delete")
        delete "/barcode/$id"(controller: "apiBarCodeEndpoints", action: "delete")

        "/register"(controller: 'register',action: 'register')
        "/register/confirm"(controller: 'register', action: "confirm")
        "/"(controller: 'main', action:'home')
        "/verify"(controller: "main", action: "verify")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
