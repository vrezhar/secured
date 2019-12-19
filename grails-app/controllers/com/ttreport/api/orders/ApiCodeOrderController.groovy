package com.ttreport.api.orders

import com.ttreport.api.current.remains.OrderService
import com.ttreport.api.resources.current.OrderAndResponse
import com.ttreport.api.resources.current.OrderCommand
import com.ttreport.data.products.remains.Order
import com.ttreport.datacenter.OmsApiConnectorService
import grails.rest.RestfulController

class ApiCodeOrderController extends RestfulController<OrderCommand>
{

    OmsApiConnectorService omsApiConnectorService
    OrderService orderService

    ApiCodeOrderController()
    {
        super(OrderCommand)
    }

    ApiCodeOrderController(Class<OrderCommand> resource, boolean readOnly) {
        super(resource, readOnly)
    }

    ApiCodeOrderController(Class<OrderCommand> resource) {
        this(resource, false)
    }

    def order(OrderCommand cmd)
    {
        OrderAndResponse orderAndResponse = orderService.processOrder(cmd)
        Order order = orderAndResponse.order

        if(!order){
            Map response = orderAndResponse.response
            withFormat {
                this.response.status = response.status as int
                json{
                    respond(response)
                }
            }
        }
        withFormat {
            Map response = omsApiConnectorService.createOrder(order)
            json{
                respond(response)
            }
        }
    }

    def checkStatus()
    {
        Map response = omsApiConnectorService.checkStatus(Order.findWhere(orderId: params.orderId), params.gtin as String)
        withFormat {
            this.response.status = response.status as int
            json{
                respond(response)
            }
        }
    }

    def getBarCodes()
    {
        Map response = omsApiConnectorService.fetchBarCodes(Order.findWhere(orderId: params.orderId), params.quantity as int, params.gtin as String)
        withFormat {
            this.response.status = response.status as int
            json{
                respond(response)
            }
        }
    }
}
