package com.ttreport.api.resources.current

import grails.validation.Validateable

class OrderUnitCommand implements Validateable
{
    long id = 0
    String description
    int cost
    int tax
    String gtin
    int quantity
    String serialNumberType = 'OPERATOR'

    static constraints = {
        id notEqual: 0
        gtin validator: { String value, OrderUnitCommand object ->
            if (!value) {
                return 'order.product.gtin.null'
            }
        }

        quantity validator: { int value, OrderUnitCommand object ->
            if (value == 0) {
                return 'order.product.quantity.null'
            }
        }

        description validator: { String value, OrderUnitCommand object ->
            if (!value) {
                return 'order.product.description.null'
            }
        }

        cost validator: { int value, OrderUnitCommand object ->
            if (value == 0) {
                return 'order.product.cost.null'
            }
        }

        tax validator: { int value, OrderUnitCommand object ->
            if (value == 0) {
                return 'order.product.tax.null'
            }
        }
    }
}
