package com.ttreport.api.orders

import com.ttreport.api.current.remains.OrderService
import com.ttreport.api.resources.current.OrderCommand
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
        withFormat {
            json{
                respond(omsApiConnectorService.createOrder(orderService.createOrder(cmd)))
            }
        }
    }

    def checkStatus()
    {

    }

    def getBarCode()
    {

    }
}
