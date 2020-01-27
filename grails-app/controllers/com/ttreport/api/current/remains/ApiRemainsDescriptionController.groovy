package com.ttreport.api.current.remains

import com.ttreport.api.current.ProductsManagerService
import com.ttreport.api.resources.current.documents.remains.RemainsDescriptionDocumentCommand
import com.ttreport.logs.ServerLogger
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

@Secured(['permitAll'])
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
            ServerLogger.log("Call to RemainDescriptionController's method, response is ${response}")
            json{
                respond(response)
            }
        }
    }

}
