package com.secured.api.resources

import com.secured.logs.DevCycleLogger
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class ProductCommand implements  Validateable
{
    int tax
    int cost
    String product_code
    String product_description
    String uit_code
    String uitu_code
    String action



    static constraints = {
        product_code validator:{String value, ProductCommand object ->
            if(value == null || value == "")
            {
                //DevCycleLogger.log("product_code not validated")
                return false
            }
            return true
        }
        action validator: { String value, ProductCommand object ->
            if(value != "SAVE" && value != "UPDATE")
            {
                //DevCycleLogger.log("wrong action name")
                return false
            }
            return true
        }
        uit_code nullable: true, validator: { String value, ProductCommand object ->
            if(object?.action == "SAVE" && (object?.uitu_code == null || object?.uitu_code == "")  && (value == null || value == ""))
            {
                //DevCycleLogger.log("uit code not validated")
                return false
            }
            return true
        }
        uitu_code nullable: true, validator: { String value, ProductCommand object ->
            if(object?.action == "SAVE" && (object?.uit_code == null || object?.uit_code == "")  && (value == null || value == ""))
            {
                //DevCycleLogger.log("uitu code not validated")
                return false
            }
            return true
        }
        product_description validator: { String value, ProductCommand object ->
            if(object?.action == "SAVE" && (value == null || value == ""))
            {
                //DevCycleLogger.log("description not validated")
                return false
            }
            return true
        }
        cost validator: { int value, ProductCommand object ->
            if(object?.action == "SAVE" && (value == 0))
            {
                //DevCycleLogger.log("cost not validated")
                return false
            }
            return true
        }
        tax validator: { int value, ProductCommand object ->
            if(object?.action == "SAVE" && (value == 0))
            {
                //DevCycleLogger.log("tax not validated")
                return false
            }
            return true
        }
    }
}
