package com.secured.api

import com.secured.api.resources.CompanyBuildingSource
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
        String response = companyService.registerCompany(src)
        if(response == "INVALID_TOKEN")
        {
            withFormat {
                json{
                    respond status: 401
                }
            }
        }
        if(response == "INVALID_INPUT")
        {
            withFormat {
                json{
                    respond status: 400
                }
            }
        }
        withFormat {
            json{
                respond([company_token: response, status: 200])
            }
        }
    }

    def update(CompanyBuildingSource src)
    {
        def response = companyService.update(src)
        if(response == "INVALID_TOKEN")
        {
            withFormat {
                json{
                    respond status: 401
                }
            }
        }
        withFormat {
            json{
                respond([new_companny_token: response, status: 200])
            }
        }
    }
}
