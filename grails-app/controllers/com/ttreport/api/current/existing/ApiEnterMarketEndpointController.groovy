package com.ttreport.api.current.existing


import com.ttreport.api.resources.current.documents.DocumentCommand
import com.ttreport.api.resources.current.documents.MarketEntranceCommand
import com.ttreport.logs.ServerLogger
import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class ApiEnterMarketEndpointController
{

    MarketEntranceDocumentService marketEntranceDocumentService

    def index()
    {
        MarketEntranceCommand cmd = DocumentCommand.bind(request?.JSON as Map,"MARKET_ENTRANCE") as MarketEntranceCommand
        Map response = marketEntranceDocumentService.enterMarket(cmd)
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
