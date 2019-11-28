package com.ttreport.data.documents.differentiated

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class GenericDocument extends Document
{
    String documentDate
    String documentNumber

    @Override
    transient Map<String,Object> getAsMap()
    {
        Map<String,Object> map = super.getAsMap()
        map.document_date = documentDate
        map.document_number = documentNumber
        return map
    }

    static constraints = {
        importFrom Document
        documentNumber nullable: false, blank: false
        documentDate nullable: false, blank: false
    }
}
