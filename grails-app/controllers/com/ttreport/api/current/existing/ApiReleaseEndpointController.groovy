package com.ttreport.api.current.existing


import com.ttreport.api.resources.current.documents.DocumentCommand
import com.ttreport.api.resources.current.documents.ReleaseCommand
import com.ttreport.logs.ServerLogger
import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class ApiReleaseEndpointController {

    ConsumerReleaseDocumentService consumerReleaseDocumentService

    def index()
    {
        ReleaseCommand cmd = DocumentCommand.bind(request?.JSON as Map, "RELEASE") as ReleaseCommand
        Map response = consumerReleaseDocumentService.release(cmd)
        ServerLogger.print_logs()
        ServerLogger.cleanup()
        this.response.status = response.status as int
        withFormat {
            json {
                respond(response)
            }
        }
    }
}
