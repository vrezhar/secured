package com.secured.api

import com.secured.api.resources.ShipmentDocumentCommand
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

@Secured(['permitAll'])
class ApiDocumentShipmentEndpointController extends RestfulController<ShipmentDocumentCommand>
{

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

    }
}
