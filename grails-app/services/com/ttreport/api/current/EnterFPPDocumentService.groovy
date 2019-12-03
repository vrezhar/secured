package com.ttreport.api.current

import com.ttreport.api.resources.current.DocumentAndResponse
import com.ttreport.api.resources.current.FromPhysCommand
import com.ttreport.api.resources.current.ShipmentDocumentCommand
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.datacenter.DataCenterApiConnectorService
import com.ttreport.logs.DevCycleLogger
import grails.async.Promise
import grails.gorm.transactions.Transactional

import static grails.async.Promises.task
import static grails.async.Promises.waitAll

@Transactional
class EnterFPPDocumentService extends ProductsManagerService
{

    Map enter(FromPhysCommand cmd)
    {
        DevCycleLogger.log("enter() called")
        DocumentAndResponse dandr = enterFPP(cmd)
        Document document = dandr.document
        if(!document){
            return dandr.response
        }
        if(!document.validate()){
            DevCycleLogger.log_validation_errors(document, "document not validated, exiting enter()")
            return dandr.response
        }
        DevCycleLogger.log("document validated, saving, exiting enter()")
        dandr.response.status = 200
        try{
            document.save(true)
        }
        catch (Exception e){
            DevCycleLogger.log(e.message)
            dandr.response.status = 500
        }
        return dandr.response
    }
}
