package com.ttreport.api.current


import com.ttreport.api.resources.current.AcceptanceDocumentCommand
import com.ttreport.api.resources.current.DocumentAndResponse
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.datacentre.DataCentreApiConnectorService
import com.ttreport.logs.DevCycleLogger
import grails.gorm.transactions.Transactional


import static grails.async.Promises.task
import static grails.async.Promises.waitAll

@Transactional
class DocumentAcceptanceService extends ProductsManagerService
{
    DataCentreApiConnectorService dataCentreApiConnectorService
    Map accept(AcceptanceDocumentCommand cmd)
    {
        DevCycleLogger.log("accept() called ")
        DocumentAndResponse dandr = acceptProducts(cmd)
        Document document = dandr.document
        if(!document){
            return dandr.response
        }
        if(!document.validate()){
            DevCycleLogger.log_validation_errors(document, "document not validated exiting accept()")
            return dandr.response
        }
        DevCycleLogger.log("document validated, saving, exiting accept()")
        def sendDocument = task{dataCentreApiConnectorService.getAcceptanceResponse(document)}
        sendDocument.then {
            DevCycleLogger.log("Data centre response received, processing...")
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
        return dandr.response
    }

}
