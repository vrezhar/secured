package com.ttreport.api.orders

import com.ttreport.api.current.remains.OrderService
import com.ttreport.api.resources.current.OrderAndResponse
import com.ttreport.api.resources.current.OrderCommand
import com.ttreport.data.products.remains.Orders
import com.ttreport.datacenter.OmsService
import com.ttreport.logs.ServerLogger
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
        try {
            OrderAndResponse orderAndResponse = orderService.processOrder(cmd)
            Orders order = orderAndResponse.order

            if(!order){
                Map response = orderAndResponse.response
                withFormat {
                    this.response.status = response.status as int
                    ServerLogger.log("Call to OrderController's order method, response is ${response}")
                    json{
                        respond(response)
                    }
                }
                return
            }
            Map response = omsService.createOrder(order)
            withFormat {
                this.response.status = response.status as int
                ServerLogger.log("Call to OrderController's order method, response is ${response}")
                json{
                    respond(response)
                }
            }
        }
        catch (Exception e) {
            ServerLogger.log_exception(e)
            withFormat {
                this.response.status = 500
                ServerLogger.log("Internal server error, controller: Order, action: order")
                json{
                    respond(status: 500)
                }
            }
        }
    }

    def checkStatus()
    {
        if(!params.orderId){
            withFormat {
                this.response.status = 402
                ServerLogger.log("Call to OrderController's checkstatus method, response is ${[status: 402, reason: "orderId missing from the url"]}")
                json{
                    respond([status: 402, reason: "orderId missing from the url"])
                }
            }
            return
        }
        if(!params.gtin){
            withFormat {
                this.response.status = 402
                ServerLogger.log("Call to OrderController's checkstatus method, response is ${[status: 402, reason: "gtin missing from the url"]}")
                json{
                    respond([status: 402, reason: "gtin missing from the url"])
                }
            }
            return
        }
        Map response = omsService.checkStatus(Orders.findWhere(orderId: params.orderId as String), params.gtin as String)
        withFormat {
            ServerLogger.log("Call to OrderController's checkStatus method, response is ${response}")
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
                ServerLogger.log("Call to OrderController's getBarCodes method, response is ${[status: 402, reason: "orderId missing from the url"]}")
                json{
                    respond([status: 402, reason: "orderId missing from the url"])
                }
            }
            return
        }
        if(!params.gtin){
            withFormat {
                this.response.status = 402
                ServerLogger.log("Call to OrderController's checkStatus method, response is ${[status: 402, reason: "gtin missing from the url"]}")
                json{
                    respond([status: 402, reason: "gtin missing from the url"])
                }
            }
            return
        }
        if(!params.quantity){
            withFormat {
                this.response.status = 402
                ServerLogger.log("Call to OrderController's checkStatus method, response is ${[status: 402, reason: "quantity missing from the url"]}")
                json{
                    respond([status: 402, reason: "quantity missing from the url"])
                }
            }
            return
        }
        Map response = omsService.fetchBarCodes(Orders.findWhere(orderId: params.orderId), params.quantity as int, params.gtin as String)
        withFormat {
            this.response.status = response.status as int
            ServerLogger.log("Call to OrderController's checkStatus method, response is ${response}")
            json{
                respond(response)
            }
        }
    }
}
