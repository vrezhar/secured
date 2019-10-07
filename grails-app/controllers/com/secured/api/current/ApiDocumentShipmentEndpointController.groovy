package com.secured.api.current

import com.secured.api.current.DocumentShipmentService
import com.secured.api.resources.current.ShipmentDocumentCommand
import com.secured.logs.DevCycleLogger
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

@Secured(['permitAll'])
class ApiDocumentShipmentEndpointController extends RestfulController<ShipmentDocumentCommand>
{

    DocumentShipmentService documentShipmentService

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
        def response = documentShipmentService.ship(cmd)
        DevCycleLogger.print_logs()
        DevCycleLogger.cleanup()
        this.response.status = response.status as int
        withFormat {
            json{
                respond(response)
            }
        }
    }
}