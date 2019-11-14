package com.ttreport.data.documents.differentiated.existing

import com.ttreport.data.documents.differentiated.GenericDocument
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class AcceptanceDocument extends GenericDocument
{

    String releaseOrderNumber
    String tradeSenderInn
    String tradeOwnerInn
    String tradeSenderName
    String tradeOwnerName
    String tradeRecipientInn
    long acceptanceDate

    @Override
    Map<String,Object> getAsMap()
    {
        Map<String,Object> map = super.getAsMap()
        map.release_order_number = releaseOrderNumber
        map.trade_sender_inn = tradeSenderInn
        map.trade_sender_name = tradeSenderName
        map.trade_owner_inn = tradeOwnerInn
        map.trade_owner_name = tradeOwnerName
        map.trade_recipient_inn = tradeRecipientInn
        map.acceptance_date = acceptanceDate
        return map
    }

    static constraints = {
        importFrom GenericDocument
        requestType validator: { String value, AcceptanceDocument object ->
            if(value != "ACCEPTANCE"){
                return false
            }
        }
        releaseOrderNumber nullable: false, blank: false
        tradeSenderInn nullable: false, blank: false
        tradeSenderName nullable: false, blank: false
        tradeOwnerInn nullable: false, blank: false
        tradeOwnerName nullable: false, blank: false
        tradeRecipientInn nullable: false, blank: false
        acceptanceDate notEqual: 0L
    }
}
