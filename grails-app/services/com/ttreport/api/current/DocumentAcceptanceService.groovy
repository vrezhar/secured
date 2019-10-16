package com.ttreport.api.current


import com.ttreport.api.resources.current.AcceptanceDocumentCommand
import com.ttreport.api.resources.current.DocumentAndResponse
import com.ttreport.data.Document
import com.ttreport.logs.DevCycleLogger
import grails.gorm.transactions.Transactional

@Transactional
class DocumentAcceptanceService extends ProductsManagerService
{

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
        document.save(true)
        return dandr.response
    }

}
