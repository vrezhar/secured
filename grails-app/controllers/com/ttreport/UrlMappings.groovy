package com.ttreport

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        post "/company"(controller: "apiCompanyEndpoints", action: "save")
        put "/company"(controller: "apiCompanyEndpoints", action: "update")
        post "/barcode/$id"(controller: "apiBarCodeEndpoints", action: "update")
        put "/barcode/$id"(controller: "apiBarCodeEndpoints", action: "delete")


        post "/document/acceptance"(controller: "apiDocumentAcceptanceEndpoint", action: "accept")
        post "/document/shipment"(controller: "apiDocumentShipmentEndpoint", action: "ship")

        post "/document/alternative/acceptance"(controller: "apiAlternativeAccept", action: "accept")
        put "/document/alternative/acceptance/$id"(controller: "apiAlternativeAccept", action: "update")
        put "/document/alternative/shipment/$id"(controller: "apiAlternativeShip", action: "ship")

        "/companies"(controller: "user", action: "showCompanies")
        "/companies/create"(controller: "user", action: "createCompany")

        "/user/company"(controller: "user", action: "createCompany")
        post "/user/company/sign"(controller: "user", action: "sign")
        "/user/company/confirm"(controller: "user", action: "confirm")
        "/register"(controller: 'register',action: 'register')
        "/register/confirm"(controller: 'register', action: "confirm")
        "/"(controller: 'user', action:'profile')
        "/verify"(controller: "main", action: "verify")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
