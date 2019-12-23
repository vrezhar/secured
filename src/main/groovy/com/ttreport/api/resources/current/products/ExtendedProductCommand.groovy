package com.ttreport.api.resources.current.products

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
        tnved_code validator: { String value, ExtendedProductCommand object ->
            if (!value) {
                return 'command.code.entrance.tnved.null'
            }
        }
        certificate_document validator: { String value, ExtendedProductCommand object ->
            if (!value) {
                return 'command.entrance.certificate.null'
            }
        }
        certificate_document_date validator: { String value, ExtendedProductCommand object ->
            if (!value) {
                return 'command.entrance.certificate.date.null'
            }
        }
        certificate_document_number validator: { String value, ExtendedProductCommand object ->
            if (!value) {
                return 'command.entrance.certificate.number.null'
            }
        }
        production_date nullable: true, blank: true
        producer_inn validator: { String value, ExtendedProductCommand object ->
            if (!value) {
                return 'command.code.entrance.tnved.null'
            }
        }
        owner_inn nullable: true, blank: true
    }
}
