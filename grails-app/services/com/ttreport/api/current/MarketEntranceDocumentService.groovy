package com.ttreport.api.current

import com.ttreport.api.resources.current.DocumentAndResponse
import com.ttreport.api.resources.current.MarketEntranceCommand
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.datacenter.DataCenterApiConnectorService
import com.ttreport.logs.DevCycleLogger
import grails.gorm.transactions.Transactional

import static grails.async.Promises.task
import static grails.async.Promises.waitAll

@Transactional
class MarketEntranceDocumentService extends ProductsManagerService
{
    DataCenterApiConnectorService dataCenterApiConnectorService

    Map enterMarket(MarketEntranceCommand cmd)
    {
        DevCycleLogger.log("enterMarket() called")
        DocumentAndResponse dandr = enterProductsIntoMarket(cmd)
        Document document = dandr.document
        if(!document){
            return dandr.response
        }
        if(!document.validate()){
            DevCycleLogger.log_validation_errors(document, "document not validated exiting enterMarket()")
            return dandr.response
        }
        DevCycleLogger.log("document validated, saving, waiting for Data center's response")
        def sendDocument = task{
            dataCenterApiConnectorService.getMarketEntryResponse(document)
        }
        sendDocument.then {
            DevCycleLogger.log("Data center response received, processing...")
            if(it != 200){
                //resolve errors or modify response by reporting failure
            }
        }

        dandr.response.status = waitAll(sendDocument)[0] as int
        if(dandr.response.status == 200){
            try{
                document.save(true)
            }
            catch (Exception e){
                DevCycleLogger.log(e.message)
                dandr.response.status = 500
            }
        }
        DevCycleLogger.log("exiting enterMarket()")
        return dandr.response
    }
}
