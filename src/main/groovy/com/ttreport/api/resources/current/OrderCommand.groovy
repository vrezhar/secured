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
        contactPerson validator: { String value, OrderCommand object ->
            if (!value) {
                return 'order.contact.person.null'
            }
        }
        releaseMethodType nullable: false, blank: false, validator: { String value, OrderCommand object ->
            if(value != 'REMAINS' && value != 'PRODUCTION' && value != 'IMPORT' && value != 'CROSSBORDER'){
                return 'order.release.method.invalid'
            }
        }
        createMethodType nullable: false, blank: false, validator: { String value, OrderCommand object ->
            if(value != 'SELF_MADE' && value != 'CEM'){
                return 'order.creation.method.invalid'
            }
        }
        products validator: { List<OrderUnitCommand> value, OrderCommand object ->
            if(!value || value?.isEmpty()){
                return 'order.product.null'
            }
        }
        companyToken nullable: false, blank: false
    }
}
