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
        post "/api/document/shipment/cancel/$number"(controller: "apiDocumentShipmentEndpoint", action: "cancel")
        post "/api/document/entrance"(controller: "apiEnterMarketEndpoint")
        post "/api/document/shipment/release"(controller: "apiReleaseEndpoint")
        post "/api/document/individual"(controller: "apiFPPEndpoint", action: "index")

        /*
        post "/document/alternative/acceptance"(controller: "apiAlternativeAccept", action: "accept")
        put "/document/alternative/acceptance/$id"(controller: "apiAlternativeAccept", action: "update")
        put "/document/alternative/shipment/$id"(controller: "apiAlternativeShip", action: "ship")
        */

        "/user/companies"(controller: "user", action: "showCompanies")
        "/user/companies/create"(controller: "user", action: "createCompany")

        post "/user/company/sign"(controller: "user", action: "sign") //used by frontend
        "/user/company/confirm"(controller: "user", action: "confirm") //used by frontend
        "/recover/email"(controller: 'main', action: 'forgotPassword')
        "/recover/confirm"(controller: 'passwordRecovery', action: 'verify')
        "/recover/validate"(controller: 'passwordRecovery', action: 'validate')
        "/password/recover"(controller: 'passwordRecovery', action: 'recover')
        "/test"(controller: "test", action: "index")
        "/register"(controller: 'register',action: 'register')
        "/profile"(controller: 'user', action: 'profile')
        "/register/confirm"(controller: 'register', action: "confirm")
        "/home"(controller: 'user', action:'profile')
        "/"(controller: "main", action: "home")
        "/about"(controller: "main", action: "aboutUs")
        "/contact"(controller: "main", action: "contactUs")
        "/verify"(controller: "main", action: "verify")

        "500"(view:'/error')
        "404"(view:'/notFound')
        "403"(view: '/accessDenied')
    }
}
