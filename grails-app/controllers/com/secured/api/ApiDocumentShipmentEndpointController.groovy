package com.secured.api

import com.secured.api.resources.ShipmentDocumentCommand
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

@Secured(['permitAll'])
class ApiDocumentShipmentEndpointController extends RestfulController<ShipmentDocumentCommand>
{

    DocumentService documentService

    ApiDocumentShipmentEndpointController()
    {
        super(ShipmentDocumentCommand)
    }

    ApiDocumentShipmentEndpointController(Class<ShipmentDocumentCommand> domainClass, boolean readonly)
    {
        super(domainClass,readonly)
    }

    ApiDocumentShipmentEndpointController(Class<ShipmentDocumentCommand> domainClass)
    {
        this(domainClass,false)
    }

    def ship(ShipmentDocumentCommand cmd)
    {
        def response = documentService.ship(cmd)
        this.response.status = response.status as int
        withFormat {
            json{
                respond(response)
            }
        }
    }
}
