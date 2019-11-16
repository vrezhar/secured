package com.ttreport.api.current

import com.ttreport.api.resources.current.DocumentCommand
import com.ttreport.api.resources.current.ReleaseCommand
import com.ttreport.logs.DevCycleLogger
import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class ApiReleaseEndpointController {

    ConsumerReleaseDocumentService consumerReleaseDocumentService

    def index()
    {
        ReleaseCommand cmd = DocumentCommand.bind(request?.JSON as Map, "RELEASE") as ReleaseCommand
        Map response = consumerReleaseDocumentService.release(cmd)
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
