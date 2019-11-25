package com.ttreport.api.current

import com.ttreport.api.resources.current.DocumentAndResponse
import com.ttreport.api.resources.current.ReleaseCommand
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.datacenter.DataCenterApiConnectorService
import com.ttreport.logs.DevCycleLogger
import grails.async.Promise
import grails.gorm.transactions.Transactional

import static grails.async.Promises.task
import static grails.async.Promises.waitAll

@Transactional
class ConsumerReleaseDocumentService extends ProductsManagerService
{
    DataCenterApiConnectorService dataCenterApiConnectorService

    Map release(ReleaseCommand cmd)
    {
        DevCycleLogger.log("release() called")
        DocumentAndResponse dandr = releaseProducts(cmd)
        Document document = dandr.document
        if(!document){
            return dandr.response
        }
        if(!document.validate()){
            DevCycleLogger.log_validation_errors(document, "document not validated exiting release()")
            return dandr.response
        }
        DevCycleLogger.log("document validated, saving, waiting for Data center's response")
        Promise<Map> sendDocument = task{
            dataCenterApiConnectorService.getReleaseResponse(document)
        }
        sendDocument.then {
            DevCycleLogger.log("Data center response received, processing...")
            //Process the response
        }

        dandr.response.status = sendDocument.get().status as int
        if(dandr.response.status == 200){
            try{
                document.save(true)
            }
            catch (Exception e){
                DevCycleLogger.log(e.message)
                dandr.response.status = 500
            }
        }
        DevCycleLogger.log("exiting release()")
        return dandr.response
    }
}
