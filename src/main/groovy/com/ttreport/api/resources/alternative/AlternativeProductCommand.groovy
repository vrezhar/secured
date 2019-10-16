package com.ttreport.api.resources.alternative

import com.ttreport.data.Products
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class AlternativeProductCommand implements Validateable
{
    int tax = -1
    int cost = -1
    String product_code
    long product_id = -1
    String product_description = ""
    List<CodeCommand> codes
    boolean rejected = false

    static  constraints = {
        codes nullable: true, validator: { List<CodeCommand> value, AlternativeProductCommand object ->
            if(value.isEmpty())
                return false
        }
        product_description nullable: true, blank: true
        product_code nullable: true, blank: true, validator: { String value, AlternativeProductCommand object ->
            if((object?.product_id == -1)  && (value == null || value == ""))
            {
                return false
            }
        }
        product_id validator: { long value, AlternativeProductCommand object ->
            if((object?.product_code == null || object?.product_code == "")  && (value == -1 || !Products.get(value)))
            {
                return false
            }
        }
    }
}
