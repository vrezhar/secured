package com.ttreport.datacenter


import com.ttreport.api.types.Endpoint
import com.ttreport.data.Company
import com.ttreport.data.products.BarCode
import com.ttreport.data.products.CodeTailEncoded
import com.ttreport.data.products.Products
import com.ttreport.data.products.remains.Order
import com.ttreport.data.products.remains.RemainsBarCode
import com.ttreport.logs.ServerLogger
import grails.gorm.transactions.Transactional
//import grails.async.Promise
import groovy.json.JsonSlurper

//import static grails.async.Promises.*

//TODO add proper logs
@Transactional
class OmsApiConnectorService extends SigningService
{
    static final String test_url = 'https://intuot.crpt.ru:12011/api/v2/light'
    static final String prod_url = 'https://suz2.crpt.ru/api/v2/light'

    static final Map<Endpoint, String> endpoints = [
            (Endpoint.CODE_ORDER)        : '/orders',
            (Endpoint.CODE_ORDER_STATUS) : '/buffer/status',
            (Endpoint.CODE_ORDER_FETCH)  : '/codes'
    ]

    protected static APIHttpClient initializeClient(Company company, Endpoint endpoint, String method = "POST", boolean testing = true)
    {
        APIHttpClient client = new APIHttpClient()
        client.targetUrl = (testing ? test_url : prod_url) + endpoints[endpoint]
        client.method = method
        client.connectingToOMS = true
        client.addHeader("clientToken",company.omsToken)
        client.addRequestParameter("omsId", company.omsId)
        return client
    }

    Map createOrder(Order order, boolean testing = true)
    {
        if(!order){
            return [status: 500]
        }
        String json = order.json()
        APIHttpClient client = initializeClient(order.company, Endpoint.CODE_ORDER, "POST", testing)
        client.addHeader("X-Signature", sign(json.bytes, true).encodeBase64().toString())
        client.data = json
        Map response = new JsonSlurper().parseText(client.sendRequest()) as Map
        if(response.fieldErrors || response.globalErrors) {
            response.status = 402
            return response
        }
        order.orderId = response.orderId
        order.estimatedWaitTime = response.expectedCompleteTimestamp as Long
        order.save()
        response.status = 200
        return response
    }

    Map checkStatus(Order order, String gtin, boolean testing = true)
    {
        if(!order){
            return [status: 404, error: "order not found"]
        }
        APIHttpClient client = initializeClient(order.company, Endpoint.CODE_ORDER_STATUS, "GET", testing)
        client.addRequestParameter("orderId", order.orderId)
        client.addRequestParameter("gtin", gtin)
        Map response = new JsonSlurper().parseText(client.sendRequest()) as Map
        if(response.fieldErrors && response.globalErrors) {
            response.status = 402
            return response
        }
        order.orderStatus = response.buffferStatus
        order.save()
        response.status = 200
        return response
    }

    Map fetchBarCodes(Order order, int quantity, String gtin, boolean testing = true)
    {
        if(!order){
            return [status: 404, error: "order not found"]
        }
        if(!order.orderId || order.orderStatus != "ACTIVE"){
            return [status: 402, error: "order inactive"]
        }
        APIHttpClient client = initializeClient(order.company, Endpoint.CODE_ORDER_STATUS, "GET", testing)
        client.addRequestParameter("quantity", quantity.toString())
        client.addRequestParameter("gtin", gtin)
        if(order.lastBlockId){
            client.addRequestParameter("lastBlockId", order.lastBlockId)
        }
        Map response = new JsonSlurper().parseText(client.sendRequest()) as Map
        if(response.fieldErrors && response.globalErrors) {
            response.status = 402
            return response
        }
        order.lastBlockId = response.lastBlockId
        order.save()
        Iterator<String> iterator = (response.codes as List<String>)?.iterator()
        if(!iterator){
            return [status: 402, error: 'no codes left for this gtin in this order']
        }
        for(orderUnit in order.products){
            if(orderUnit.gtin != gtin){
                continue
            }
            Products products = Products.get(orderUnit.productId)
            if(!products){
                ServerLogger.log('critical logic error, product not found')
                return [status: 500]
            }
            response.productId = products.id
            for(int i = 0; i < quantity; ++i){
                String code = iterator.next()
                if(!code){
                    //TODO test this case properly
                    return [status: 202, warning: 'no codes left for this gtin in this order', productId: products.id]
                }
                try{
                    CodeTailEncoded tail = new CodeTailEncoded(encodedTail: "\u001d"+code.split("\u001d")[1])
                    BarCode barCode = new RemainsBarCode(uituCode: code.split("\u001d")[0], tail: tail, products: products, inMarket: false)
                    products.addToBarCodes(barCode)
                    products.save()
                    tail.save()
                    barCode.save()
                }
                catch (Exception e){
                    ServerLogger.log_exception(e)
                    return [status: 500]
                }
            }
            break
        }
        response.status = 200
        return response
    }
}
