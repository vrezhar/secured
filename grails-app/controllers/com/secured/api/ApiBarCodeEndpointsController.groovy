package com.secured.api

import com.secured.api.resources.BarCodeRegisteringSource
import com.secured.data.BarCode
import com.secured.logs.DevCycleLogger
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
        DevCycleLogger.print_logs()
        DevCycleLogger.cleanup()
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
        DevCycleLogger.print_logs()
        DevCycleLogger.cleanup()
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
        DevCycleLogger.print_logs()
        DevCycleLogger.cleanup()
        this.response.status = (response.status as int)
        withFormat {
            json{
                respond(response)
            }
        }
    }
}
