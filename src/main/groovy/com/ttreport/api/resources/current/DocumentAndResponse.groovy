package com.ttreport.api.resources.current

import com.ttreport.data.documents.differentiated.Document
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class DocumentAndResponse
{
    Document document
    Map response
}
