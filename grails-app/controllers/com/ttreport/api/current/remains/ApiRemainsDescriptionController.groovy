package com.ttreport.api.current.remains

import com.ttreport.api.current.ProductsManagerService
import com.ttreport.api.resources.current.documents.remains.RemainsDescriptionDocumentCommand
import grails.rest.RestfulController

class ApiRemainsDescriptionController extends RestfulController<RemainsDescriptionDocumentCommand>
{
    ProductsManagerService productsManagerService

    ApiRemainsDescriptionController()
    {
        super(RemainsDescriptionDocumentCommand)
    }

    ApiRemainsDescriptionController(Class<RemainsDescriptionDocumentCommand> resource, boolean readOnly)
    {
        super(resource, readOnly)
    }

    ApiRemainsDescriptionController(Class<RemainsDescriptionDocumentCommand> resource)
    {
        this(resource, false)
    }

    def describeRemains(RemainsDescriptionDocumentCommand cmd)
    {
        Map response = productsManagerService.describeRemains(cmd).response
        withFormat {
            this.response.status = response.status as int
            json{
                respond(response)
            }
        }
    }

}
