package com.ttreport.api.current.existing

import com.ttreport.api.current.ProductsManagerService
import com.ttreport.api.resources.current.DocumentAndResponse
import com.ttreport.api.resources.current.ReleaseCommand
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.datacenter.DataCenterApiConnectorService
import com.ttreport.logs.ServerLogger
import grails.gorm.transactions.Transactional

@Transactional
class ConsumerReleaseDocumentService extends ProductsManagerService
{
    DataCenterApiConnectorService dataCenterApiConnectorService

    Map release(ReleaseCommand cmd)
    {
        ServerLogger.log("release() called")
        DocumentAndResponse dandr = releaseProducts(cmd)
        Document document = dandr.document
        if(!document){
            return dandr.response
        }
        if(!document.validate()){
            ServerLogger.log_validation_errors(document, "document not validated exiting release()")
            return dandr.response
        }
        ServerLogger.log("document validated, saving, waiting for Data center's response")
        dandr.response.status = 200
        try{
            document.save(true)
        }
        catch (Exception e){
            ServerLogger.log(e.message)
            dandr.response.status = 500
        }
        ServerLogger.log("exiting release()")
        return dandr.response
    }
}
