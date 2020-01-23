package com.ttreport.api.current.remains

import com.ttreport.MessageBundleService
import com.ttreport.api.resources.current.OrderAndResponse
import com.ttreport.api.resources.current.OrderCommand
import com.ttreport.data.Company
import com.ttreport.data.products.Products
import com.ttreport.data.products.remains.Orders
import com.ttreport.data.products.remains.ProductOrderUnit
import grails.gorm.transactions.Transactional
import org.springframework.transaction.TransactionStatus

@Transactional
class OrderService extends MessageBundleService
{
    static scope = 'prototype'

    //TODO add proper logs
    OrderAndResponse processOrder(OrderCommand cmd)
    {
        if(!cmd.validate()){
            List errorList = []
            cmd.errors.fieldErrors.each {
                errorList.add([field: it.field, value: it.rejectedValue, error: getMessage(it.code)?: "invalid value"])
            }
            return new OrderAndResponse(response: [status: 402, errors: errorList])
        }
        Company company = cmd.authorize()
        if(!company){
            return new OrderAndResponse(response: [status: 400])
        }
        OrderAndResponse orderAndResponse = new OrderAndResponse()
        Orders order = new Orders(contactPerson: cmd.contactPerson, releaseMethodType: cmd.releaseMethodType, createMethodType: cmd.createMethodType, company: company)
        Map<String,Object> response = [:]
        response.status = 200
        List errors = []
        Orders.withTransaction { TransactionStatus status ->
            boolean noerrors = true
            for(orderUnit in cmd.products){
                if(!orderUnit.validate()){
                    List errorList = []
                    orderUnit.errors.fieldErrors.each {
                        errorList.add([field: it.field, value: it.rejectedValue, error: getMessage(it.code)?: "invalid value"])
                    }
                    errors.add([gtin: orderUnit.gtin, errors: errorList])
                    noerrors = false
                    continue
                }
                Products products = Products.get(orderUnit.id)
                if(!products){
                    List errorList = []
                    orderUnit.errors.rejectValue('id','command.product.notfound')
                    orderUnit.errors.fieldErrors.each {
                        errorList.add([field: it.field, value: it.rejectedValue, error: getMessage(it.code)?: "invalid value"])
                    }
                    errors.add([gtin: orderUnit.gtin, errors: errorList])
                    noerrors = false
                    continue
                }
                products.description = orderUnit.description?: products.description
                products.cost = orderUnit.cost?: products.cost
                products.tax = orderUnit.tax?: products.tax
                ProductOrderUnit unit = new ProductOrderUnit(productId: products.id, order: order)
                unit.gtin = orderUnit.gtin
                unit.quantity = orderUnit.quantity
                if(orderUnit.serialNumberType){
                    unit.serialNumberType = orderUnit.serialNumberType
                }
                order.addToProducts(unit)
            }
            if(!noerrors){
                status.setRollbackOnly()
                return
            }
            return
        }
        if(!errors){
            orderAndResponse.order = order
            orderAndResponse.response = response
            return orderAndResponse
        }
        response.status = 402
        response.errors = errors
        orderAndResponse.response = response
        return orderAndResponse
    }
}
