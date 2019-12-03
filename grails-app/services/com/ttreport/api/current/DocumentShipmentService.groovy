package com.ttreport.api.current


import com.ttreport.api.resources.current.DocumentAndResponse
import com.ttreport.api.resources.current.ShipmentDocumentCommand
import com.ttreport.api.types.DocumentType
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.data.documents.differentiated.existing.ShipmentDocument
import com.ttreport.datacenter.DataCenterApiConnectorService
import com.ttreport.logs.ServerLogger
import grails.gorm.transactions.Transactional

@Transactional
class DocumentShipmentService extends ProductsManagerService
{

    DataCenterApiConnectorService dataCenterApiConnectorService

    Map ship(ShipmentDocumentCommand cmd)
    {
        ServerLogger.log("ship() called")
        DocumentAndResponse dandr = shipProducts(cmd)
        Document document = dandr.document
        if(!document){
            return dandr.response
        }
        if(!document.validate()){
            ServerLogger.log_validation_errors(document, "document not validated exiting ship()")
            return dandr.response
        }
        ServerLogger.log("document validated, saving, exiting ship()")
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

    def cancelShipment(String shipmentNumber)
    {
        ShipmentDocument document = ShipmentDocument.findWhere(documentNumber: shipmentNumber)
        if(!document){
            return [status: 412]
        }
        return dataCenterApiConnectorService.sendDocument(document, DocumentType.CANCEL_SHIPMENT, true)
    }
}
