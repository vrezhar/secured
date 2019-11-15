package com.ttreport.api.current

import com.ttreport.api.resources.current.DocumentAndResponse
import com.ttreport.api.resources.current.DocumentCommand
import com.ttreport.api.resources.current.MarketEntranceCommand
import com.ttreport.logs.DevCycleLogger

class ApiEnterMarketEndpointController {

    MarketEntranceDocumentService marketEntranceDocumentService

    def index()
    {
        try{
            MarketEntranceCommand cmd = DocumentCommand.bind(request.JSON as Map) as MarketEntranceCommand
            marketEntranceDocumentService.enterMarket(cmd)
        }
        catch (Exception e)
        {
            DevCycleLogger.log(e.message)
        }
    }
}
