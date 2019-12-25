package com.ttreport.data.products.remains

import com.ttreport.data.products.Products

class FullRemainsProduct extends Products
{
    String model
    String productName
    String brand
    String country
    String productType
    String materialUpper
    String materialLining
    String materialDown
    String color
    String productSize
    String certificateType
    String certificateDate
    String certificateNumber


    static constraints = {
        model nullable: false, blank: false
        productName nullable: false, blank: false
        brand nullable: false, blank: false
        country nullable: true, blank: true
        productType nullable: false, blank: false
        materialUpper nullable: false, blank: false
        materialLining nullable: false, blank: false
        materialDown nullable: false, blank: false
        color nullable: true, blank: true
        productSize nullable: true, blank: true
        certificateType nullable: false, blank: false
        certificateDate nullable: false, blank: false
        certificateNumber nullable: false, blank: false
    }
}
