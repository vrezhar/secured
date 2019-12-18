package com.ttreport.data.products.remains

import com.ttreport.data.Company
import com.ttreport.logs.ServerLogger
import groovy.json.JsonBuilder

class Order
{
    String contactPerson
    String releaseMethodType = 'REMAINS'
    String createMethodType = 'SELF_MADE'
    String productOrderId
    String orderId
    String orderStatus
    String lastBlockId
    long estimatedWaitTime = 1

    static hasMany = [products: ProductOrderUnit]
    static belongsTo = [company: Company]

    Date dateCreated
    Date lastUpdated

    transient Map serialize()
    {
        Map map = [:]
        map.contactPerson = contactPerson
        map.releaseMethodType = releaseMethodType
        map.createMethodType = createMethodType
        if(productOrderId){
            map.productOrderId = productOrderId
        }
        map.put('products', [])
        products.each {
            (map.products as List).add(it.serialize())
        }
        return map
    }

    transient String json()
    {
        JsonBuilder builder = new JsonBuilder(this.serialize())
        ServerLogger.log("Order serialized", builder.toPrettyString())
        return builder.toString()
    }

    static constraints = {
        productOrderId nullable: true, blank: true
        contactPerson nullable: false, blank: false
        releaseMethodType nullable: false, blank: false, validator: { String value, Order object ->
            if(value != 'REMAINS' || value != 'PRODUCTION' || value != 'IMPORT' || value != 'CROSSBORDER'){
                return false
            }
        }
        createMethodType nullable: false, blank: false, validator: { String value, Order object ->
            if(value != 'SELF_MADE' || value != 'CEM'){
                return false
            }
        }
        orderStatus nullable: true, blank: false
        orderId nullable: true, blank: true
        lastBlockId nullable: true, blank: true
    }
}
