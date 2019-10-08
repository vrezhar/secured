package com.secured.api.resources.current

import com.secured.data.Document
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class DocumentCommand implements Validateable
{
    List<ProductCommand> products
    int document_date = 0
    int transfer_date = 0
    String turnover_type
    String document_number
    String companyToken

    static constraints = {
        document_date nullable: false, notEqual: 0
        transfer_date nullable: false, notEqual: 0
        document_number nullable: false, blank: false, validator: {String value, DocumentCommand object ->
            if(Document.findByDocumentNumber(value)){
                return false
            }
        }
        turnover_type nullable: false, blank: false
        products nullable: false, validator: { List<ProductCommand> value, DocumentCommand object ->
            if(value?.isEmpty()) {
                return false
            }
        }
        companyToken nullable: false, blank: false
    }

}
