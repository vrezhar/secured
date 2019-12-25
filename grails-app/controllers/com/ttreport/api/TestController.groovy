package com.ttreport.api


import com.ttreport.api.resources.current.products.ProductCommand
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

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
