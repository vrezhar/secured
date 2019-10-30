package com.ttreport

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        post "/api/company"(controller: "apiCompanyEndpoints", action: "save")
        put "/api/company"(controller: "apiCompanyEndpoints", action: "update")
        post "/api/barcode/$id"(controller: "apiBarCodeEndpoints", action: "update")
        put "/api/barcode/$id"(controller: "apiBarCodeEndpoints", action: "delete")


        post "/api/document/acceptance"(controller: "apiDocumentAcceptanceEndpoint", action: "accept")
        post "/api/document/shipment"(controller: "apiDocumentShipmentEndpoint", action: "ship")

        /*
        post "/document/alternative/acceptance"(controller: "apiAlternativeAccept", action: "accept")
        put "/document/alternative/acceptance/$id"(controller: "apiAlternativeAccept", action: "update")
        put "/document/alternative/shipment/$id"(controller: "apiAlternativeShip", action: "ship")
        */

        "/user/companies"(controller: "user", action: "showCompanies")
        "/user/companies/create"(controller: "user", action: "createCompany")

        post "/user/company/sign"(controller: "user", action: "sign") //used by frontend
        "/user/company/confirm"(controller: "user", action: "confirm") //used by frontend
        "/register"(controller: 'register',action: 'register')
        "/profile"(controller: 'user', action: 'profile')
        "/register/confirm"(controller: 'register', action: "confirm")
        "/"(controller: 'user', action:'profile')
        "/verify"(controller: "main", action: "verify")

        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
