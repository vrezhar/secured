package com.secured.api.deprecated


import com.secured.api.resources.deprecated.CompanyBuildingSource
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
