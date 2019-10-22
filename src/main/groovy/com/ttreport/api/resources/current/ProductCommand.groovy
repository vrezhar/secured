package com.ttreport.api.resources.current

import com.ttreport.data.BarCode
import com.ttreport.data.Products
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
        product_code nullable: true, validator:{String value, ProductCommand object ->
            if((!value || !Products.get(product_code)) && object?.action != "SAVE") {
                return 'command.product.notfound'
            }
        }
        action validator: { String value, ProductCommand object ->
            if(value != "SAVE" && value != "UPDATE" && value != "DELETE") {
                return false
            }
        }

        uit_code nullable: true, validator: { String value, ProductCommand object ->
            BarCode exists = BarCode.findWhere(uit_code: value, uitu_code: uitu_code)
            if(object?.action == "SAVE" && !object?.uitu_code && !value) {
                return 'command.code.uit.invalid'
            }
            if(!exists && object?.action == "DELETE") {
                return 'command.code.notfound'
            }
            if(exists && object?.action != "DELETE"){
                return 'command.code.duplicate'
            }
            if(exists && exists?.dateDeleted){
                return  'command.code.deleted'
            }
            if(object?.uitu_code && value){
                return 'command.code.both'
            }
        }
        uitu_code nullable: true, validator: { String value, ProductCommand object ->
            BarCode exists = BarCode.findWhere(uitu_code: value, uit_code: uit_code)
            if(object?.action == "SAVE" && !object?.uit_code && !value) {
                return 'command.code.uitu.invalid'
            }
            if(!exists && object?.action == "DELETE") {
                return 'command.code.notfound'
            }
            if(exists && object?.action != "DELETE"){
                return 'command.code.duplicate'
            }
            if(exists && exists?.dateDeleted){
                return  'command.code.deleted'
            }
            if(object?.uit_code && value){
                return 'command.code.both'
            }
        }
        product_description nullable: true, validator: { String value, ProductCommand object ->
            if(object?.action == "SAVE" && !value) {
                return 'command.product.invalid'
            }
        }
        cost validator: { int value, ProductCommand object ->
            if(object?.action == "SAVE" && (value == 0)) {
                return 'command.product.invalid'
            }
        }
        tax validator: { int value, ProductCommand object ->
            if(object?.action == "SAVE" && (value == 0)) {
                return 'command.product.invalid'
            }
        }
    }
}
