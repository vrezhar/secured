package com.ttreport.data.products

import com.ttreport.data.products.BarCode

class MarketEntranceBarCode extends BarCode
{

    String tnvedCode
    String certificateDocument
    String certificateDocumentDate
    String certificateDocumentNumber
    String productionDate
    String producerInn
    String ownerInn
    static constraints = {
        importFrom BarCode
        tnvedCode nullable: false, blank: false
        certificateDocument nullable: false, blank: false
        certificateDocumentDate nullable: false, blank: false
        certificateDocumentNumber nullable: false, blank: false
        productionDate nullable: true, blank: true
        producerInn nullable: false, blank: false
        ownerInn nullable: true, blank: true
    }
}
