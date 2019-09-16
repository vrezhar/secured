package com.secured.api

import com.secured.api.resources.CompanyBuildingSource
import com.secured.api.resources.TokenRegeneratingSource
import grails.plugin.springsecurity.annotation.Secured

@Secured(["permitAll"])
class ApiCompanyEndpointsController
{
    def save(CompanyBuildingSource src)
    {

    }

    def update(TokenRegeneratingSource src)
    {

    }
}
