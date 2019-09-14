package com.secured.api

import com.secured.auth.BarCode
import com.secured.auth.Company
import com.secured.auth.User
import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import org.grails.web.json.JSONElement

@Secured(['permitAll'])
class ApiTestController extends RestfulController<User> {

    ApiTestController() {
        super(User)
    }

    def index()
    {
        def admin = queryForResource(1)
        def company = Company.findOrSaveWhere(address: "New York", user: admin)
        def barcode = BarCode.findOrSaveWhere(code: "test", company: company)
        admin.save(true)

        JSON jsonobject = new JSON()
        jsonobject = admin as JSON

        withFormat {
            json{
                render jsonobject
            }
        }
    }

    @Override
    protected User queryForResource(Serializable id) {
        User.where {
            id == id
        }.find()
    }
}
