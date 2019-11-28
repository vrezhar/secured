package com.ttreport.data.documents.differentiated.existing

import com.ttreport.api.resources.current.DocumentForm
import com.ttreport.data.BarCode
import com.ttreport.data.Company
import com.ttreport.data.documents.differentiated.Document

class RFIEntranceDocument extends Document
{
    String participantInn
    @Override
    transient Map<String,Object> getAsMap()
    {
        Map<String,Object> map =
                [
                        participant_inn: participantInn?: company?.inn,
                        product_list: barCodes?.collect{
                            [uit: it.uitCode]
                        }
                ]
        return map
    }

    static constraints = {
        importFrom Document
        participantInn nullable: true, blank: false
    }
}