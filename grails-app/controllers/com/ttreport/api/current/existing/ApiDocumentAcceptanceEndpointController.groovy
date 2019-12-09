package com.ttreport.api.current.existing


import com.ttreport.api.resources.current.AcceptanceDocumentCommand
import com.ttreport.logs.ServerLogger
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

@Secured(['permitAll'])
class ApiDocumentAcceptanceEndpointController extends RestfulController<AcceptanceDocumentCommand>
{
    DocumentAcceptanceService documentAcceptanceService

    ApiDocumentAcceptanceEndpointController()
    {
        super(AcceptanceDocumentCommand)
    }

    ApiDocumentAcceptanceEndpointController(Class<AcceptanceDocumentCommand> domainClass, boolean readonly)
    {
        super(domainClass,readonly)
    }

    ApiDocumentAcceptanceEndpointController(Class<AcceptanceDocumentCommand> domainClass)
    {
        this(domainClass,false)
    }

    def accept(AcceptanceDocumentCommand cmd)
    {
        Map response = documentAcceptanceService.accept(cmd)
        ServerLogger.print_logs()
        ServerLogger.cleanup()
        this.response.status = response.status as int
        withFormat {
            json{
                respond(response)
            }
        }
    }
}
