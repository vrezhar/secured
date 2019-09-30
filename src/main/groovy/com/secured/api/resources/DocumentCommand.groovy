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

    static Document createMock(DocumentCommand cmd)
    {
        Document document = new Document()
        document.documentDate = cmd.document_date
        document.transferDate = cmd.transfer_date
        document.turnoverType = cmd.turnover_type
        document.documentNumber = cmd.document_number
        cmd.products.each {
            Products product = ProductCommand.createOrUpdate(it)
            document.products.add(product)
        }
        return document
    }

    static constraints = {
        document_date nullable: false, min: 0
        transfer_date nullable: false, min: 0
        document_number nullable: false, blank: false
        turnover_type nullable: false, blank: false
        products nullable: false
        companyToken nullable: false, blank: false
    }

}
