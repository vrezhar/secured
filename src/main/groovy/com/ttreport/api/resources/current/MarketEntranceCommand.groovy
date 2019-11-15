package com.ttreport.api.resources.current

class MarketEntranceCommand extends DocumentCommand
{
    String production_date
    String producer_inn
    String owner_inn
    String production_type
    String doc_type

    static MarketEntranceCommand createFromBase(DocumentCommand cmd)
    {
        MarketEntranceCommand command = new MarketEntranceCommand()
        command.products = cmd?.products
        command.companyToken = cmd?.companyToken
        command.document_number = cmd?.document_number
        command.document_date = cmd?.document_date
        return command
    }

    static constraints = {
        importFrom DocumentCommand
        production_type nullable: false, blank: false
        producer_inn nullable: true, blank: true
        production_date nullable: false, blank: false
        doc_type nullable: false, blank: false
        owner_inn nullable: true, blank: true
    }

}
