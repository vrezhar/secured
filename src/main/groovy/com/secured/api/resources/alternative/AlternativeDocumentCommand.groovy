package com.secured.api.resources.alternative

import com.secured.api.resources.current.ProductCommand
import grails.validation.Validateable

class AlternativeDocumentCommand implements Validateable
{
    List<AlternativeProductCommand> products
    String companyToken
    int document_date = 0
    int transfer_date = 0
    String turnover_type
    String document_number

    static constraints = {
        document_date nullable: false, notEqual: 0
        transfer_date nullable: false, notEqual: 0
        document_number nullable: false, blank: false
        turnover_type nullable: false, blank: false
        products nullable: false, validator: { List<ProductCommand> value, AlternativeDocumentCommand object ->
            if(value?.isEmpty())
            {
                return false
            }
        }
        companyToken nullable: false, blank: false
    }
}
