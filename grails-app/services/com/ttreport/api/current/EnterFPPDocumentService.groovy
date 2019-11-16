package com.ttreport.api.current

import com.ttreport.api.resources.current.DocumentAndResponse
import com.ttreport.api.resources.current.FromPhysCommand
import com.ttreport.api.resources.current.ShipmentDocumentCommand
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.datacenter.DataCenterApiConnectorService
import com.ttreport.logs.DevCycleLogger
import grails.gorm.transactions.Transactional

import static grails.async.Promises.task
import static grails.async.Promises.waitAll

@Transactional
class EnterFPPDocumentService extends ProductsManagerService
{

    DataCenterApiConnectorService dataCenterApiConnectorService

    Map enter(FromPhysCommand cmd)
    {
        DevCycleLogger.log("enter() called")
        DocumentAndResponse dandr = enterFPP(cmd)
        Document document = dandr.document
        if(!document){
            return dandr.response
        }
        if(!document.validate()){
            DevCycleLogger.log_validation_errors(document, "document not validated enter ship()")
            return dandr.response
        }
        DevCycleLogger.log("document validated, saving, exiting enter()")
        def sendDocument = task{dataCenterApiConnectorService.getFPEntryResponse(document)}
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
