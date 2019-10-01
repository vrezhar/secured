package com.secured.api.resources

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class DocumentCommand implements Validateable
{
    List<ProductCommand> products
    int document_date
    int transfer_date
    String turnover_type
    String document_number
    String companyToken

    static constraints = {
        document_date nullable: false
        transfer_date nullable: false
        document_number nullable: false, blank: false
        turnover_type nullable: false, blank: false
        products nullable: false
        companyToken nullable: false, blank: false
    }

}
