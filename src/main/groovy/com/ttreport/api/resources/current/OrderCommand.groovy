package com.ttreport.api.resources.current

import com.ttreport.api.DocumentCommandObject
import com.ttreport.data.Company
import grails.validation.Validateable

class OrderCommand implements Validateable, DocumentCommandObject
{

    String companyToken
    String contactPerson
    String releaseMethodType = 'REMAINS'
    String createMethodType = 'SELF_MADE'
    String productOrderId
    List<OrderUnitCommand> products

    static constraints = {
        productOrderId nullable: true, blank: true
        contactPerson nullable: false, blank: false
        releaseMethodType nullable: false, blank: false, validator: { String value, OrderCommand object ->
            if(value != 'REMAINS' || value != 'PRODUCTION' || value != 'IMPORT' || value != 'CROSSBORDER'){
                return false
            }
        }
        products nullable: false
        createMethodType nullable: false, blank: false, validator: { String value, OrderCommand object ->
            if(value != 'SELF_MADE' || value != 'CEM'){
                return false
            }
        }
        companyToken nullable: false, blank: false, validator: { String value, OrderCommand object ->
            Company company = Company.findWhere(token: value)
            if(!company){
                return false
            }
        }
    }
}
