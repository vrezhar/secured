package com.ttreport.api.resources.current

import com.ttreport.data.Company
import com.ttreport.data.documents.differentiated.GenericDocument
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class DocumentCommand implements Validateable
{
    List<ProductCommand> products = []
    String document_date = "2019"
    String transfer_date = "2019"
    String turnover_type = "test"
    String document_number
    String companyToken

    static constraints = {
        document_date nullable: false, blank: false
        transfer_date nullable: false, blank: false
        document_number nullable: false, blank: false, validator: {String value, DocumentCommand object ->
            if(GenericDocument.findByDocumentNumber(value)){
                return false
            }
        }
        turnover_type nullable: false, blank: false
        products nullable: false, validator: { List<ProductCommand> value, DocumentCommand object ->
            if(value?.isEmpty()) {
                return false
            }
        }
        companyToken nullable: false, blank: false, validator: {String value, DocumentCommand object ->
            Company company = Company.findWhere(token: value)
            if(!company){
                return false
            }
        }
    }

}
