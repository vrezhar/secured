package com.ttreport.api.current


import com.ttreport.api.resources.current.DocumentAndResponse
import com.ttreport.api.resources.current.ShipmentDocumentCommand
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.data.documents.differentiated.GenericDocument
import com.ttreport.data.documents.differentiated.existing.ShipmentDocument
import com.ttreport.datacentre.DataCentreApiConnectorService
import com.ttreport.logs.DevCycleLogger
import grails.gorm.transactions.Transactional

import static grails.async.Promises.task
import static grails.async.Promises.waitAll

@Transactional
class DocumentShipmentService extends ProductsManagerService
{

    DataCentreApiConnectorService dataCentreApiConnectorService

    Map ship(ShipmentDocumentCommand cmd)
    {
        DevCycleLogger.log("ship() called")
        DocumentAndResponse dandr = shipProducts(cmd)
        Document document = dandr.document
        if(!document){
            return dandr.response
        }
        if(!document.validate()){
            DevCycleLogger.log_validation_errors(document, "document not validated exiting accept()")
            return dandr.response
        }
        DevCycleLogger.log("document validated, saving, exiting accept()")
        def sendDocument = task{dataCentreApiConnectorService.getShipmentResponse(document)}
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

    def cancelShipment(String participantInn, String shipmentNumber)
    {
        if(!ShipmentDocument.findWhere(documentNumber: shipmentNumber)){
            return 404
        }
        return dataCentreApiConnectorService.cancelShipment(participantInn,shipmentNumber)
    }
}
