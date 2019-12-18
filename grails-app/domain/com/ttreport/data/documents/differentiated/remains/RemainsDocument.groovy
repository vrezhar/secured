package com.ttreport.data.documents.differentiated.remains

import com.ttreport.data.documents.differentiated.Document

class RemainsDocument extends Document
{

    static constraints = {
        documentId nullable: true, blank: true
        documentStatus nullable: true, blank: true
        company nullable: false
        barCodes nullable: true
    }

    @Override
    Map<String, Object> getAsMap()
    {
        return [trade_participant_inn: company?.inn]
    }
}
