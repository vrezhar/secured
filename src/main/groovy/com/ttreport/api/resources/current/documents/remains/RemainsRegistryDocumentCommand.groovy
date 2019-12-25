package com.ttreport.api.resources.current.documents.remains

import com.ttreport.api.DocumentCommandObject
import com.ttreport.api.resources.current.remains.ProductRemainsRegistryCommand
import com.ttreport.data.Company
import grails.validation.Validateable

class RemainsRegistryDocumentCommand implements Validateable, DocumentCommandObject
{
    String companyToken
    List<ProductRemainsRegistryCommand> products

    static constraints = {
        companyToken nullable: false, blank: false
        products nullable: false
    }
}
