package com.ttreport.data.documents.differentiated.existing

import com.ttreport.api.resources.current.DocumentForm
import com.ttreport.data.BarCode

class RFPProductCirculationDocument implements DocumentForm
{
    String participantInn
    List<String> barCodes = []

    Date dateCreated
    Date lastUpdated

    @Override
    Map<String,Object> getAsMap()
    {
        Map<String,Object> map =
                [
                        participant_inn: participantInn,
                        product_list: barCodes?.collect{
                            [uit: it]
                        }
                ]
        return map
    }

    static constraints = {
        participantInn nullable: false, blank: false
    }
}
