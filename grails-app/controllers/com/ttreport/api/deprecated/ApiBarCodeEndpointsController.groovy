package com.ttreport.api.deprecated


import com.ttreport.api.resources.deprecated.BarCodeRegisteringSource
import com.ttreport.logs.ServerLogger
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController


@Secured(["permitAll"])
class ApiBarCodeEndpointsController extends RestfulController<BarCodeRegisteringSource>
{
    BarCodeService barCodeService

    ApiBarCodeEndpointsController()
    {
        super(BarCodeRegisteringSource)
    }

    ApiBarCodeEndpointsController(Class<BarCodeRegisteringSource> domainClass, boolean readonly)
    {
        super(domainClass,readonly)
    }

    ApiBarCodeEndpointsController(Class<BarCodeRegisteringSource> domainClass)
    {
        this(domainClass,false)
    }

    def save(BarCodeRegisteringSource src)
    {
        def response = barCodeService.save(src)
        this.response.status = (response.status as int)
        withFormat {
            json{
                respond(response)
            }
        }
    }

    def update(BarCodeRegisteringSource src)
    {
        src.productId = (params.id as int)
        def response = barCodeService.update(src)
        this.response.status = (response.status as int)
        withFormat {
            json{
                respond(response)
            }
        }
    }

    def delete(BarCodeRegisteringSource src)
    {
        src.productId = (params.id as int)
        def response = barCodeService.delete(src)
        ServerLogger.print_logs()
        ServerLogger.cleanup()
        this.response.status = (response.status as int)
        withFormat {
            json{
                respond(response)
            }
        }
    }
}
