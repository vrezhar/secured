package com.ttreport.api.resources.current

import com.ttreport.data.BarCode
import com.ttreport.data.Products
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class ProductCommand implements Validateable
{
    boolean rejected = false
    int tax = 0
    int cost = 0
    String product_code
    String product_description
    String uit_code
    String uitu_code
    String action

    static constraints = {
        product_code nullable: true, validator:{String value, ProductCommand object ->
            Products databaseInstance = Products.get(object?.product_code)
            if(!value && object?.action != "SAVE") {
                return 'command.product.null'
            }
            if(!databaseInstance && object?.action != "SAVE"){
                return 'command.product.notfound'
            }
        }
        action validator: { String value, ProductCommand object ->
            if(value != "SAVE" && value != "UPDATE" && value != "DELETE") {
                return false
            }
        }

        uit_code nullable: true, validator: { String value, ProductCommand object ->
            BarCode exists = BarCode.findWhere(uit_code: value?: null, uitu_code: object?.uitu_code?: null)
            if(object?.action == "SAVE" && !(value || object?.uitu_code)) {
                return 'command.code.uit.null'
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
            BarCode exists = BarCode.findWhere(uit_code: value?: null, uitu_code: object?.uitu_code?: null)
            if(object?.action == "SAVE" && !object?.uit_code && !value) {
                return 'command.code.uitu.null'
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
                return 'command.product.description.null'
            }
        }
        cost validator: { int value, ProductCommand object ->
            if(object?.action == "SAVE" && (value == 0)) {
                return 'command.product.cost.null'
            }
        }
        tax validator: { int value, ProductCommand object ->
            if(object?.action == "SAVE" && (value == 0)) {
                return 'command.product.tax.null'
            }
        }
    }
}
