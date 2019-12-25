package com.ttreport.api.resources.current.documents

import com.ttreport.api.resources.current.documents.DocumentCommand
import grails.compiler.GrailsCompileStatic

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
