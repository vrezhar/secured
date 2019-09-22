package com.secured.data.connectors

import com.secured.data.BarCode
import com.secured.data.Products
import grails.compiler.GrailsCompileStatic
import groovy.transform.ToString


@GrailsCompileStatic
@ToString(cache=true, includeNames=true, includePackage=false)
class ProductsBarcodes implements  Serializable
{
    private static final long serialVersionUID = 1

    Products products
    BarCode barcode

    static ProductsBarcodes create(Products products, BarCode barcode, boolean flush = false)
    {
        ProductsBarcodes productsBarcodes = new ProductsBarcodes(products: products, barcode: barcode)
        productsBarcodes.save(flush)
        return productsBarcodes
    }

    static constraints = {
    }
}
