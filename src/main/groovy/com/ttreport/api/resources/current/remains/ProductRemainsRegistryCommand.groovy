package com.ttreport.api.resources.current.remains

import com.ttreport.data.products.BarCode
import com.ttreport.data.products.Products
import com.ttreport.validation.Validator
import grails.validation.Validateable

class ProductRemainsRegistryCommand implements Validateable
{

    long id = 0
    String ki
    String kitu
    boolean rejected = false
    static constraints = {
        id notEqual: 0L, validator: { String value, ProductRemainsRegistryCommand object ->
            if(value && !Products.get(object?.id)){
                return 'command.code.notfound'
            }
        }

        ki nullable: true, validator: { String value, ProductRemainsRegistryCommand object ->
            if (!value && !object?.kitu) {
                return 'command.code.uit.null'
            }
            if(value){
                if(!Validator.validateBarCodeFormat(value)){
                    return 'command.code.format.invalid'
                }
            }
            BarCode exists = BarCode.findWhere(uitCode: value ?: null, uituCode: object?.kitu ?: null)
            if(!exists){
                return 'command.code.notfound'
            }
            if (exists && exists.inMarket) {
                return 'command.code.duplicate'
            }
        }

        kitu nullable: true, validator: { String value, ProductRemainsRegistryCommand object ->
            if (!value && !object?.ki) {
                return 'command.code.uitu.null'
            }
            if(value){
                if(!Validator.validateBarCodeFormat(value)){
                    return 'command.code.format.invalid'
                }
            }
        }
    }
}
