package com.secured.api.current


import com.secured.api.resources.current.AcceptanceDocumentCommand
import com.secured.api.resources.current.DocumentAndResponse
import com.secured.api.resources.current.ProductCommand
import com.secured.api.response.current.Response
import com.secured.data.Company
import com.secured.data.Document
import com.secured.data.Products
import com.secured.logs.DevCycleLogger
import grails.gorm.transactions.Transactional
import grails.validation.Validateable

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
        DevCycleLogger.log("saving, exiting accept()")
        document.save(true)
        return dandr.response
    }

}
