package com.secured.api

import com.secured.api.resources.BarCodeRegisteringSource
import grails.plugin.springsecurity.annotation.Secured

@Secured(["permitAll"])
class ApiBarCodeEndpointsController
{
    def save(BarCodeRegisteringSource src)
    {

    }

    def delete(BarCodeRegisteringSource src)
    {

    }
}
