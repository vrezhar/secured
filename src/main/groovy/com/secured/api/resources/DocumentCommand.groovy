package com.secured.api.resources

import grails.validation.Validateable
import com.secured.data.Document
import com.secured.data.Products

class DocumentCommand implements Validateable
{
    List<ProductCommand> products
    long document_date
    long transfer_date
    String turnover_type
    String document_number
    String companyToken

    static constraints = {
        document_date nullable: false, min: 0
        transfer_date nullable: false, min: 0
        document_number nullable: false, blank: false
        turnover_type nullable: false, blank: false
        products nullable: false
        companyToken nullable: false, blank: false
    }

}
