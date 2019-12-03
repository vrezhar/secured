package com.ttreport.api.current


import com.ttreport.api.resources.current.AcceptanceDocumentCommand
import com.ttreport.api.resources.current.DocumentAndResponse
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.datacenter.DataCenterApiConnectorService
import com.ttreport.logs.ServerLogger
import grails.gorm.transactions.Transactional

@Transactional
class DocumentAcceptanceService extends ProductsManagerService
{
    DataCenterApiConnectorService dataCenterApiConnectorService
    Map accept(AcceptanceDocumentCommand cmd)
    {
        ServerLogger.log("accept() called ")
        DocumentAndResponse dandr = acceptProducts(cmd)
        Document document = dandr.document
        if(!document){
            return dandr.response
        }
        if(!document.validate()){
            ServerLogger.log_validation_errors(document, "document not validated exiting accept()")
            return dandr.response
        }
//        DevCycleLogger.log("document validated, saving, waiting for Data center's response...")
//        Promise<Map> sendDocument = task {
//            dataCenterApiConnectorService.getAcceptanceResponse(document)
//        }
//        sendDocument.then {
//            DevCycleLogger.log("Data center response received, processing...")
//            //Process the response
//        }

        dandr.response.status = 200
        try{
            document.save(true)
        }
        catch (Exception e){
            ServerLogger.log(e.message)
            dandr.response.status = 500
        }
        ServerLogger.log("exiting accept()")
        return dandr.response
    }

}
