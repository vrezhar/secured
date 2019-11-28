package com.ttreport.api

import com.ttreport.api.resources.current.ExtendedProductCommand
import com.ttreport.api.resources.current.ProductCommand
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import grails.validation.Validateable

@Secured(['permitAll'])
class TestController extends RestfulController<ProductCommand>
{
    TestController()
    {
        super(ProductCommand)
    }

    TestController(Class<ProductCommand> resource, boolean readOnly)
    {
        super(resource, readOnly)
    }

    TestController(Class<ProductCommand> resource)
    {
        this(resource,false)
    }

    def index()
    {

        withFormat {
            json{
                respond([:])
            }
        }
    }
}
