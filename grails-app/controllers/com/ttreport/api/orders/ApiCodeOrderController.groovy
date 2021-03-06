package com.ttreport.api.orders

import com.ttreport.api.current.remains.OrderService
import com.ttreport.api.resources.current.OrderAndResponse
import com.ttreport.api.resources.current.OrderCommand
import com.ttreport.data.products.remains.Orders
import com.ttreport.datacenter.OmsService
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

@Secured(['permitAll'])
class ApiCodeOrderController extends RestfulController<OrderCommand>
{

    OmsService omsService
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
        Orders order = orderAndResponse.order

        if(!order){
            Map response = orderAndResponse.response
            withFormat {
                this.response.status = response.status as int
                json{
                    respond(response)
                }
            }
            return
        }
        Map response = omsService.createOrder(order)
        withFormat {
            this.response.status = response.status as int
            json{
                respond(response)
            }
        }
    }

    def checkStatus()
    {
        if(!params.orderId){
            withFormat {
                this.response.status = 402
                json{
                    respond([status: 402, reason: "orderId missing from the url"])
                }
            }
            return
        }
        if(!params.gtin){
            withFormat {
                this.response.status = 402
                json{
                    respond([status: 402, reason: "gtin missing from the url"])
                }
            }
            return
        }
        Map response = omsService.checkStatus(Orders.findWhere(orderId: params.orderId as String), params.gtin as String)
        withFormat {
            this.response.status = response.status as int
            json{
                respond(response)
            }
        }
    }

    def getBarCodes()
    {
        if(!params.orderId){
            withFormat {
                this.response.status = 402
                json{
                    respond([status: 402, reason: "orderId missing from the url"])
                }
            }
            return
        }
        if(!params.gtin){
            withFormat {
                this.response.status = 402
                json{
                    respond([status: 402, reason: "gtin missing from the url"])
                }
            }
            return
        }
        if(!params.quantity){
            withFormat {
                this.response.status = 402
                json{
                    respond([status: 402, reason: "quantity missing from the url"])
                }
            }
            return
        }
        Map response = omsService.fetchBarCodes(Orders.findWhere(orderId: params.orderId), params.quantity as int, params.gtin as String)
        withFormat {
            this.response.status = response.status as int
            json{
                respond(response)
            }
        }
    }
}
