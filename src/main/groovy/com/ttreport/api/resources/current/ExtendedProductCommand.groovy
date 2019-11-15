package com.ttreport.api.resources.current

class ExtendedProductCommand extends ProductCommand
{

    String tnved_code
    String certificate_document
    String certificate_document_date
    String certificate_document_number
    String production_date = ""
    String producer_inn
    String owner_inn = ""

    static ExtendedProductCommand createFromBase(ProductCommand cmd)
    {
        ExtendedProductCommand command = new ExtendedProductCommand()
        command.uit_code = cmd?.uit_code
        command.uitu_code = cmd?.uitu_code
        command.tax = cmd?.tax as int
        command.cost = cmd?.cost as int
        command.id = cmd?.id as long
        command.product_description = cmd?.product_description
        return command
    }

    static constraints = {
        importFrom ProductCommand
        tnved_code nullable: false, blank: false
        certificate_document nullable: false, blank: false
        certificate_document_date nullable: false, blank: false
        certificate_document_number nullable: false, blank: false
        production_date nullable: true, blank: true
        producer_inn nullable: false, blank: false
        owner_inn nullable: true, blank: true
    }
}
