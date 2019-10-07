package com.secured.api.current

import com.secured.api.current.DocumentService
import com.secured.api.resources.current.DocumentAndResponse
import com.secured.api.resources.current.ProductCommand
import com.secured.api.resources.current.ShipmentDocumentCommand
import com.secured.api.response.current.Response
import com.secured.data.Company
import com.secured.data.Document
import com.secured.data.Products
import com.secured.logs.DevCycleLogger
import grails.gorm.transactions.Transactional
import grails.validation.Validateable

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
            DevCycleLogger.log_validation_errors(document as Validateable, "document with number ${document.documentNumber} not validated, exiting ship()")
            return dandr.response
        }
        document.save(true)
        DevCycleLogger.log("saving document with number ${document.documentNumber}, exiting accept()")
        return dandr.response
    }
}
