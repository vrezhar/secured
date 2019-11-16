package com.ttreport.api.resources.current

class ReleaseCommand extends DocumentCommand
{
    String order_number
    String order_date
    String action
    String action_date
    int document_type

    static ReleaseCommand createFromBase(DocumentCommand cmd)
    {
        ReleaseCommand command = new ReleaseCommand()
        command.products = cmd?.products
        command.companyToken = cmd?.companyToken
        command.document_number = cmd?.document_number
        command.document_date = cmd?.document_date
        return command
    }

    static constraints = {
        importFrom DocumentCommand
        order_number nullable: false, blank: false
        order_date nullable: true, blank: true
        action_date nullable: false, blank: false
        action nullable: false, blank: false
    }
}