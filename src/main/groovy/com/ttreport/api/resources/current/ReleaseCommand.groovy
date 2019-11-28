package com.ttreport.api.resources.current

class ReleaseCommand extends GenericDocumentCommand
{
    String order_number
    String order_date
    int action
    String action_date
    int document_type

    static ReleaseCommand createFromBase(DocumentCommand cmd)
    {
        ReleaseCommand command = new ReleaseCommand()
        command.products = cmd?.products
        command.companyToken = cmd?.companyToken
        return command
    }

    static constraints = {
        importFrom DocumentCommand
        order_number nullable: false, blank: false
        order_date nullable: true, blank: true
        action_date nullable: false, blank: false
        action validator: { int value, ReleaseCommand object ->
            if(!(value >= 0 && value <= 6)){
                return  false
            }
        }
    }
}
