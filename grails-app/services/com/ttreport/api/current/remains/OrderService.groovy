package com.ttreport.api.current.remains

import com.ttreport.api.resources.current.OrderCommand
import com.ttreport.data.products.remains.Order
import grails.gorm.transactions.Transactional

@Transactional
class OrderService
{
    static scope = 'prototype'

    Order createOrder(OrderCommand cmd) {

    }
}
