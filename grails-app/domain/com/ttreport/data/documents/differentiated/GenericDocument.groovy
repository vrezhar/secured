package com.ttreport.data.documents.differentiated

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class GenericDocument extends Document
{
    String requestType
    String turnoverType
    String transferDate

    @Override
    Map<String,Object> getAsMap()
    {
        Map<String,Object> map = super.getAsMap()
        map.request_type = requestType
        map.turnover_type = turnoverType
        map.transfer_date = transferDate
        return map
    }

    static constraints = {
        importFrom Document
        transferDate nullable: false
        turnoverType validator: { String value, GenericDocument object ->
            if(value != "SALE" && value != "COMMISSION" && value != "AGENT" && value != "CONTRACT")
                return false
        }
    }
}
