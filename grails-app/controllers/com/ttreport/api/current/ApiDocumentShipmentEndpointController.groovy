package com.ttreport.api.current


import com.ttreport.api.resources.current.ShipmentDocumentCommand
import com.ttreport.data.documents.differentiated.existing.ShipmentDocument
import com.ttreport.logs.DevCycleLogger
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
        Map response = documentShipmentService.ship(cmd)
        DevCycleLogger.print_logs()
        DevCycleLogger.cleanup()
        this.response.status = response.status as int
        withFormat {
            json{
                respond(response)
            }
        }
    }

    def cancelShipment()
    {
        def response = documentShipmentService.cancelShipment(params.number as String)
        this.response.status = response
        withFormat {
            json{
                respond([status: response])
            }
        }
    }
}
