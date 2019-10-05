package com.secured.api.resources.current


import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class ProductCommand implements Validateable
{
    int tax = 0
    int cost = 0
    String product_code
    String product_description
    String uit_code
    String uitu_code
    String action



    static constraints = {
        product_code validator:{String value, ProductCommand object ->
            if(!value) {
                return false
            }
            return true
        }
        action validator: { String value, ProductCommand object ->
            if(value != "SAVE" && value != "UPDATE")
            {
                return false
            }
        }
        uit_code nullable: true, validator: { String value, ProductCommand object ->
            if(object?.action == "SAVE" && !object?.uitu_code && !value ) {
                return false
            }
        }
        uitu_code nullable: true, validator: { String value, ProductCommand object ->
            if(object?.action == "SAVE" && !object?.uit_code && !value ) {
                return false
            }
        }
        product_description validator: { String value, ProductCommand object ->
            if(object?.action == "SAVE" && !value) {
                return false
            }
        }
        cost validator: { int value, ProductCommand object ->
            if(object?.action == "SAVE" && (value == 0)) {
                return false
            }
        }
        tax validator: { int value, ProductCommand object ->
            if(object?.action == "SAVE" && (value == 0)) {
                return false
            }
        }
    }
}
