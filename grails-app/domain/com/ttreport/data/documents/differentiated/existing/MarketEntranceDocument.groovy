package com.ttreport.data.documents.differentiated.existing

import com.ttreport.data.documents.differentiated.Document

class MarketEntranceDocument extends Document
{
    String productionDate
    String producerInn = company?.inn
    String ownerInn = company?.inn
    String productionType
    String docType

    @Override
    transient Map<String, Object> getAsMap()
    {
        Map<String, Object> map = super.getAsMap()
        map.remove("document_number")
        map.doc_type = docType
        map.document_description =
                [
                        participant_inn: company?.inn,
                        production_date: productionDate,
                        producer_inn: producerInn,
                        owner_inn: ownerInn,
                        production_type: productionType
                ]
        return map
    }

    static constraints = {
        company nullable: false
        barCodes nullable: false
        productionType nullable: false, blank: false
        producerInn nullable: false, blank: false
        productionDate nullable: false, blank: false
        docType nullable: false, blank: false
        ownerInn nullable: false, blank: false
    }

}
