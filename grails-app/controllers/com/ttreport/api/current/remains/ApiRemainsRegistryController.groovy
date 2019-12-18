package com.ttreport.api.current.remains

import com.ttreport.api.resources.current.documents.remains.RemainsDescriptionDocumentCommand
import com.ttreport.api.resources.current.documents.remains.RemainsRegistryDocumentCommand
import grails.rest.RestfulController

class ApiRemainsRegistryController extends RestfulController<RemainsRegistryDocumentCommand>
{

    ApiRemainsRegistryController()
    {
        super(RemainsRegistryDocumentCommand)
    }

    ApiRemainsRegistryController(Class<RemainsRegistryDocumentCommand> resource, boolean readOnly)
    {
        super(resource, readOnly)
    }

    ApiRemainsRegistryController(Class<RemainsRegistryDocumentCommand> resource)
    {
        this(resource, false)
    }


    def registerRemains(RemainsRegistryDocumentCommand cmd)
    {

    }
}
