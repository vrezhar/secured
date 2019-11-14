package com.ttreport.data.documents.differentiated.existing

import com.ttreport.data.documents.differentiated.Document
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class AcceptanceDocument extends Document{

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
        map.release_order_number = this.releaseOrderNumber
        map.trade_sender_inn = this.tradeSenderInn
        map.trade_sender_name = this.tradeSenderName
        map.trade_owner_inn = this.tradeOwnerInn
        map.trade_owner_name = this.tradeOwnerName
        map.trade_recipient_inn = this.tradeRecipientInn
        map.acceptance_date = this.acceptanceDate
        return map
    }

    static constraints = {
        importFrom Document
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
