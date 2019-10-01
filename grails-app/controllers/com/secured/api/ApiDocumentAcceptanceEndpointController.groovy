package com.secured.api

import com.secured.api.resources.AcceptanceDocumentCommand
import com.secured.logs.DevCycleLogger
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

@Secured(['permitAll'])
class ApiDocumentAcceptanceEndpointController extends RestfulController<AcceptanceDocumentCommand>
{
    DocumentService documentService

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
        def response = documentService.accept(cmd)
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
