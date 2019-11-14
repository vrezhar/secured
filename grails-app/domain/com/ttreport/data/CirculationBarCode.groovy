package com.ttreport.data

import com.ttreport.data.BarCode

class CirculationBarCode extends BarCode
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
        productionDate nullable: false, blank: false
        producerInn nullable: false, blank: false
        ownerInn nullable: false, blank: false
    }
}
