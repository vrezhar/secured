package com.ttreport.data.documents.differentiated.remains

import com.ttreport.data.documents.differentiated.Document
import com.ttreport.data.products.Products

class RemainsDocument extends Document
{

    static hasMany = [products: Products]

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
