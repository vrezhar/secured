package com.ttreport.api.current.existing


import com.ttreport.api.resources.current.documents.FromPhysCommand
import com.ttreport.logs.ServerLogger
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

@Secured(['permitAll'])
class ApiFPPEndpointController extends RestfulController<FromPhysCommand>
{

    EnterFPPDocumentService enterFPPDocumentService

    ApiFPPEndpointController(){
        super(FromPhysCommand)
    }

    ApiFPPEndpointController(Class<FromPhysCommand> resource) {
        this(resource, false)
    }

    ApiFPPEndpointController(Class<FromPhysCommand> resource, boolean readOnly) {
        super(resource, readOnly)
    }

    def index(FromPhysCommand cmd)
    {
        Map response = enterFPPDocumentService.enter(cmd)
        ServerLogger.print_logs()
        ServerLogger.cleanup()
        this.response.status = response.status as int
        ServerLogger.log("Call to FPPEEndpointController's method, response is ${response}")
        withFormat {
            json {
                respond(response)
            }
        }
    }
}
