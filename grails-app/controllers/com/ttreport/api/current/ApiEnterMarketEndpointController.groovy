package com.ttreport.api.current

import com.ttreport.api.resources.current.DocumentAndResponse
import com.ttreport.api.resources.current.DocumentCommand
import com.ttreport.api.resources.current.MarketEntranceCommand
import com.ttreport.logs.DevCycleLogger
import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class ApiEnterMarketEndpointController {

    MarketEntranceDocumentService marketEntranceDocumentService

    def index()
    {
        MarketEntranceCommand cmd = DocumentCommand.bind(request?.JSON as Map,"ENTRANCE") as MarketEntranceCommand
        Map response = marketEntranceDocumentService.enterMarket(cmd)
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
