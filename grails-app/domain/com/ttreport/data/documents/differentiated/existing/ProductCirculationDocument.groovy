package com.ttreport.data.documents.differentiated.existing

import com.ttreport.data.documents.differentiated.Document

class ProductCirculationDocument extends Document
{

    String participantInn
    String productionDate
    String producerInn
    String ownerInn
    String productionType
    String docType

    static constraints = {
        importFrom Document
        participantInn nullable: false, blank: false
        productionType nullable: false, blank: false
        producerInn nullable: false, blank: false
        productionDate nullable: false, blank: false
        docType nullable: false, blank: false
        ownerInn nullable: false, blank: false
    }

    @Override
    Map<String, Object> getAsMap()
    {
        Map<String, Object> map = super.getAsMap()
        map.doc_type = docType
        map.document_description =
                [
                        participant_inn: participantInn,
                        production_date: productionDate,
                        producer_inn: producerInn,
                        owner_inn: ownerInn,
                        production_type: productionType
                ]
        return map
    }
}
