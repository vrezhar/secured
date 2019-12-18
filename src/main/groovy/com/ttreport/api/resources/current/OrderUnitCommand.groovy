package com.ttreport.api.resources.current

import grails.validation.Validateable

class OrderUnitCommand implements Validateable
{
    long id
    String description
    int cost
    int tax
    String gtin
}
