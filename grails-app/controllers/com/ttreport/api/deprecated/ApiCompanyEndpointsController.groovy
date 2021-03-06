package com.ttreport.api.deprecated


import com.ttreport.api.resources.deprecated.CompanyBuildingSource
import com.ttreport.logs.ServerLogger
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

@Secured(["permitAll"])
class ApiCompanyEndpointsController extends RestfulController<CompanyBuildingSource>
{
    CompanyService companyService

    ApiCompanyEndpointsController()
    {
        super(CompanyBuildingSource)
    }

    ApiCompanyEndpointsController(Class<CompanyBuildingSource> domainClass, boolean readonly)
    {
        super(domainClass,readonly)
    }

    ApiCompanyEndpointsController(Class<CompanyBuildingSource> domainClass)
    {
        this(domainClass,false)
    }

    def save(CompanyBuildingSource src)
    {
        def response = companyService.save(src)
        ServerLogger.print_logs()
        println("command object: {'${src.mainToken}', '${src.address}', '${src.inn}'}")
        ServerLogger.cleanup()
        withFormat {
            this.response.status = (response.status as int)
            json{
                respond(response)
            }
        }
    }

    def update(CompanyBuildingSource src)
    {
        def response = companyService.update(src)
        withFormat {
            this.response.status = (response.status as int)
            json{
                respond(response)
            }
        }
    }
}
