package com.ttreport.api.resources.current

import com.ttreport.data.Company
import com.ttreport.data.documents.differentiated.GenericDocument
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class GenericDocumentCommand extends DocumentCommand
{
    String transfer_date = "2019"
    String turnover_type = "test"

    static constraints = {
        importFrom DocumentCommand
        transfer_date nullable: false, blank: false
        turnover_type nullable: false, blank: false
    }

}
