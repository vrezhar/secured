package com.secured.api

import com.secured.auth.BarCode
import com.secured.auth.Company
import com.secured.auth.User
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

@Secured(['permitAll'])
class ApiTestController extends RestfulController<User> {

    ApiTestController() {
        super(User)
    }

    def index()
    {
        def admin = User.findByUsername("admin")
        def barcode = new BarCode(code: "test")
        def company = new Company(address: "New York")
        barcode.save()
        company.addToBarCodes(barcode)
        company.save()
        admin.addToCompanies(company)
        admin.save(true)

        withFormat {
            json{
                render admin.companies[0].barCodes as JSON
            }
        }
    }
}
