package com.secured.api.resources


import grails.validation.Validateable

class BarCodeRegisteringSource implements Validateable
{
    String companyToken
    String productCode
    String productDescription
    long productId
    List<String> barcodes

    static constraints = {
        companyToken nullable: false, blank: false
        productCode nullable: true, blank: true
        productId nullable: true, blank: true
        productDescription nullable: true, blank: true
        barcodes nullable: false
    }

}
