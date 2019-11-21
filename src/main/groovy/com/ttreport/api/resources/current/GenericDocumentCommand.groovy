package com.ttreport.api.resources.current

import com.ttreport.data.Company
import com.ttreport.data.documents.differentiated.GenericDocument
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class GenericDocumentCommand extends DocumentCommand
{
    String document_number
    String document_date = new Date().toInstant().toString()

    static constraints = {
        importFrom DocumentCommand
        document_date nullable: false, blank: false
        document_number nullable: false, blank: false
    }

}
