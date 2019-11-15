package com.ttreport.data.documents.differentiated.existing

import com.ttreport.data.documents.differentiated.Document

class ConsumerReleaseDocument extends Document
{
    String orderNumber
    String orderDate
    String action
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
        map.order_date = orderDate
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
        action nullable: false, blank: false
//        documentType notEqual: 0
    }
}
