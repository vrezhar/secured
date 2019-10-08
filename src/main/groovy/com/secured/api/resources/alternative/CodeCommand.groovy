package com.secured.api.resources.alternative

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class CodeCommand implements Validateable
{
    String uit_code
    String uitu_code
    boolean rejected = false

    static  constraints = {
        uit_code nullable: true, validator: { String value, CodeCommand object ->
            if((object?.uitu_code == null || object?.uitu_code == "")  && (value == null || value == ""))
            {
                return false
            }
            return true
        }
        uitu_code nullable: true, validator: { String value, CodeCommand object ->
            if((object?.uit_code == null || object?.uit_code == "")  && (value == null || value == ""))
            {
                return false
            }
            return true
        }
    }
}
