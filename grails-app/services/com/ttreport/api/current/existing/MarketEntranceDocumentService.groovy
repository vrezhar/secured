package com.ttreport.api.current.existing

import com.ttreport.api.current.ProductsManagerService
import com.ttreport.api.resources.current.DocumentAndResponse
import com.ttreport.api.resources.current.MarketEntranceCommand
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.datacenter.DataCenterApiConnectorService
import com.ttreport.logs.ServerLogger
import grails.gorm.transactions.Transactional

@Transactional
class MarketEntranceDocumentService extends ProductsManagerService
{
    DataCenterApiConnectorService dataCenterApiConnectorService

    Map enterMarket(MarketEntranceCommand cmd)
    {
        ServerLogger.log("enterMarket() called")
        DocumentAndResponse dandr = enterProductsIntoMarket(cmd)
        Document document = dandr.document
        if(!document){
            return dandr.response
        }
        if(!document.validate()){
            ServerLogger.log_validation_errors(document, "document not validated exiting enterMarket()")
            return dandr.response
        }
        ServerLogger.log("document validated, saving, waiting for Data center's response")
        dandr.response.status = 200
        try{
            document.save(true)
        }
        catch (Exception e){
            ServerLogger.log(e.message)
            dandr.response.status = 500
        }
        ServerLogger.log("exiting enterMarket()")
        return dandr.response
    }
}
