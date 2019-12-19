package com.ttreport.api.current.remains

import com.ttreport.MessageBundleService
import com.ttreport.api.resources.current.OrderAndResponse
import com.ttreport.api.resources.current.OrderCommand
import com.ttreport.data.Company
import com.ttreport.data.products.Products
import com.ttreport.data.products.remains.Order
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
                errorList.add([field: it.field, error: getMessage(it.code)])
            }
            return [status: 402, errors: errorList]
        }
        Company company = cmd.authorize()
        if(!company){
            return [status: 400]
        }
        OrderAndResponse orderAndResponse = new OrderAndResponse()
        Order order = new Order(contactPerson: cmd.contactPerson, releaseMethodType: cmd.releaseMethodType, createMethodType: cmd.createMethodType, company: company)
        Map<String,Object> response = [:]
        response.status = 200
        List errors = []
        Order.withTransaction { TransactionStatus status ->
            def savepoint = status.createSavepoint()
            boolean noerrors = true
            for(orderUnit in cmd.products){
                if(!orderUnit.validate()){
                    List errorList = []
                    cmd.errors.fieldErrors.each {
                        errorList.add([field: it.field, error: getMessage(it.code)])
                    }
                    errors.add([gtin: orderUnit.gtin, errors: errorList])
                    noerrors = false
                    continue
                }
                Products products = Products.get(orderUnit.id)
                products.description = orderUnit.description?: products.description
                products.cost = orderUnit.cost?: products.cost
                products.tax = orderUnit.tax?: products.tax
                ProductOrderUnit unit = new ProductOrderUnit(productId: products.id, order: order)
                unit.gtin = orderUnit.gtin
                unit.quantity = orderUnit.quantity
                if(orderUnit.serialNumberType){
                    unit.serialNumberType = orderUnit.serialNumberType
                }
                unit.save()
            }
            if(!noerrors){
                status.rollbackToSavepoint(savepoint)
                return
            }
            order.save()
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
