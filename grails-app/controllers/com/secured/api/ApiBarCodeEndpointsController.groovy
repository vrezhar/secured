package com.secured.api

import com.secured.api.resources.BarCodeRegisteringSource
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
        def response_list = barCodeService.registerBarCodes(src)
        if(response_list[0] == "INVALID_TOKEN")
        {
            withFormat {
                json{
                    respond(status: 401)
                }
            }
        }
        withFormat {
            json{
                respond([barcode_list: response_list, status: 200])
            }
        }
    }

    def delete(BarCodeRegisteringSource src)
    {
        def response_list = barCodeService.delete(src)
        if(response_list[0] == "INVALID_TOKEN")
        {
            withFormat {
                json{
                    respond(status: 401)
                }
            }
        }
        if(response_list[0] == "INVALID_INPUT")
        {
            response_list.remove(0)
            withFormat {
                json{
                    respond([barcode_list: response_list,status: 400])
                }
            }
        }
        withFormat {
            json{
                respond([barcode_list: response_list,status: 200])
            }
        }
    }
}
