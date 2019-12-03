package com.ttreport.api.current

import com.ttreport.api.resources.current.DocumentAndResponse
import com.ttreport.api.resources.current.FromPhysCommand
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.logs.ServerLogger
import grails.gorm.transactions.Transactional

@Transactional
class EnterFPPDocumentService extends ProductsManagerService
{

    Map enter(FromPhysCommand cmd)
    {
        ServerLogger.log("enter() called")
        DocumentAndResponse dandr = enterFPP(cmd)
        Document document = dandr.document
        if(!document){
            return dandr.response
        }
        if(!document.validate()){
            ServerLogger.log_validation_errors(document, "document not validated, exiting enter()")
            return dandr.response
        }
        ServerLogger.log("document validated, saving, exiting enter()")
        dandr.response.status = 200
        try{
            document.save(true)
        }
        catch (Exception e){
            ServerLogger.log(e.message)
            dandr.response.status = 500
        }
        return dandr.response
    }
}
