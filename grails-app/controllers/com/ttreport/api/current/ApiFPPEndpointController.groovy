package com.ttreport.api.current

import com.ttreport.api.resources.current.FromPhysCommand
import com.ttreport.logs.DevCycleLogger
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
        DevCycleLogger.print_logs()
        DevCycleLogger.cleanup()
        this.response.status = response.status as int
        withFormat {
            json {
                respond(response)
            }
        }
    }
}
