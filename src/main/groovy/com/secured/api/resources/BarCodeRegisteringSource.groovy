package com.secured.api.resources

import grails.validation.Validateable

class BarCodeRegisteringSource implements Validateable
{
    String companyToken
    List<String> barcodes
    static constraints = {
    }
}
