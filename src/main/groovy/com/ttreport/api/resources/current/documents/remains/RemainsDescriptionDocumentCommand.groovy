package com.ttreport.api.resources.current.documents.remains

import com.ttreport.api.DocumentCommandObject
import com.ttreport.api.resources.current.remains.ProductRemainsDescriptionCommand
import com.ttreport.data.Company
import grails.validation.Validateable

class RemainsDescriptionDocumentCommand implements Validateable, DocumentCommandObject
{
    String companyToken
    List<ProductRemainsDescriptionCommand> products

    static constraints = {
        companyToken nullable: false, blank: false
        products nullable: false
    }
}
