package com.ttreport.datacentre

import com.ttreport.data.Document
import grails.async.Promise
import static grails.async.Promises.*

import grails.gorm.transactions.Transactional

@Transactional
class DataCentreApiConnectorService {

    def getAcceptanceResponse(Document document)
    {
        return 200
    }
    def getShipmentResponse(Document document)
    {
        return 200
    }
}
