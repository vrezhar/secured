package com.ttreport.datacenter.pools

import com.ttreport.data.Company

class OrderPool
{
    static class Order
    {
        String gtin
        boolean generatingSerials
        int quantity
        long completionTime
    }

    Company company
    List<Order> orders
}
