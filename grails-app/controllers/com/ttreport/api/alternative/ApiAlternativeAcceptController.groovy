package com.ttreport.api.alternative

import com.ttreport.api.resources.alternative.AlternativeAcceptanceDocumentCommand
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

@Secured(['permitAll'])
class ApiAlternativeAcceptController extends RestfulController<AlternativeAcceptanceDocumentCommand>
{

    ApiAlternativeAcceptController()
    {
        super(AlternativeAcceptanceDocumentCommand)
    }

    ApiAlternativeAcceptController(Class<AlternativeAcceptanceDocumentCommand> domainClass, boolean readonly)
    {
        super(domainClass,readonly)
    }

    ApiAlternativeAcceptController(Class<AlternativeAcceptanceDocumentCommand> domainClass)
    {
        this(domainClass,false)
    }

    def accept(AlternativeAcceptanceDocumentCommand cmd)
    {

    }

    def update(AlternativeAcceptanceDocumentCommand cmd)
    {

    }

}
