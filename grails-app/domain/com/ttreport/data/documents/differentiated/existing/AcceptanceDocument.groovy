package com.ttreport.data.documents.differentiated.existing

import com.ttreport.data.documents.differentiated.GenericDocument
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class AcceptanceDocument extends GenericDocument
{

    String turnoverType
    String transferDate
    String releaseOrderNumber
    String tradeOwnerInn
    String tradeOwnerName
    String tradeSenderInn
    String tradeSenderName
    String tradeRecipientInn = company?.inn
    String acceptanceDate

    @Override
    transient Map<String,Object> getAsMap()
    {
        Map<String,Object> map = super.getAsMap()
        map.request_type = "ACCEPTANCE"
        map.turnover_type = turnoverType
        map.transfer_date = transferDate
        map.release_order_number = releaseOrderNumber
        map.trade_sender_inn = tradeSenderInn
        map.trade_sender_name = tradeSenderName
        map.trade_owner_inn = tradeOwnerInn?: company?.inn
        map.trade_owner_name = tradeOwnerInn?: company?.name
        map.trade_recipient_inn = tradeRecipientInn
        map.acceptance_date = acceptanceDate
        return map
    }

    static constraints = {
        importFrom GenericDocument
        transferDate nullable: false, blank: false
        turnoverType validator: { String value, GenericDocument object ->
            if(value != "SALE" && value != "COMMISSION" && value != "AGENT" && value != "CONTRACT")
                return false
        }
        releaseOrderNumber nullable: false, blank: false
        tradeSenderInn nullable: false, blank: false
        tradeSenderName nullable: false, blank: false
        tradeRecipientInn nullable: false, blank: false
        acceptanceDate nullable: false, blank: false
        tradeOwnerInn nullable: true, blank: true
        tradeOwnerName nullable: true, blank: true
    }
}
