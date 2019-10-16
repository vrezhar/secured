package com.ttreport.api.current


import com.ttreport.api.resources.current.DocumentAndResponse
import com.ttreport.api.resources.current.ShipmentDocumentCommand
import com.ttreport.data.Document
import com.ttreport.logs.DevCycleLogger
import grails.gorm.transactions.Transactional

@Transactional
class DocumentShipmentService extends ProductsManagerService
{


    Map ship(ShipmentDocumentCommand cmd)
    {
        DevCycleLogger.log("ship() called")
        DocumentAndResponse dandr = shipProducts(cmd)
        Document document = dandr.document
        if(!document){
            return dandr.response
        }
        if(!document.validate() || document.products?.isEmpty()) {
            DevCycleLogger.log_validation_errors(document,"document with number ${document.documentNumber} not validated, exiting ship()")
            return dandr.response
        }
        document.save(true)
        DevCycleLogger.log("saving document with number ${document.documentNumber}, exiting ship()")
        return dandr.response
    }
}
