package com.ttreport.data.documents.differentiated.existing


import com.ttreport.data.documents.differentiated.Document

class RFIEntranceDocument extends Document
{
    String participantInn = company?.inn
    @Override
    transient Map<String,Object> getAsMap()
    {
        Map<String,Object> map =
                [
                        participant_inn: participantInn,
                        product_list: barCodes?.collect{
                            [uit: it.uitCode]
                        }
                ]
        return map
    }

    static constraints = {
        importFrom Document
        participantInn nullable: false, blank: false
    }
}
