package com.ttreport.data.documents.differentiated.existing

import com.ttreport.data.documents.differentiated.Document
import com.ttreport.data.documents.differentiated.GenericDocument

class ConsumerReleaseDocument extends Document
{
    String orderNumber
    String orderDate
    int action
    String actionDate
    int documentType

    @Override
    transient Map<String,Object> getAsMap()
    {
        barCodes.each {
            it.minified = true
        }
        Map<String,Object> map = super.getAsMap()
        map.order_number = orderNumber
        if(orderDate) {
            map.order_date = orderDate
        }
        map.inn = company?.inn
        map.action = action
        map.action_date = actionDate
        map.document_type = documentType
        return map
    }
    static constraints = {
        orderNumber nullable: false, blank: false
        orderDate nullable: true, blank: true
        actionDate nullable: false, blank: false
        action validator: { int value, ConsumerReleaseDocument object ->
            if(!(value >= 0 && value <= 6)){
                return  false
            }
        }
//        documentType notEqual: 0
    }
}
