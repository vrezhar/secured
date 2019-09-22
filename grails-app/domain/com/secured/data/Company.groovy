package com.secured.data

import com.secured.auth.User
import com.secured.data.connectors.CompanyProducts
import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@GrailsCompileStatic
@EqualsAndHashCode(includes='token')
class Company implements  Serializable
{

    private static final long serialVersionUID = 1

    String address
    String companyId
    String token = UUID.randomUUID().toString()
    Date dateCreated
    Date lastUpdated

    Set<Products> getProducts()
    {
        (CompanyProducts.findAllByCompany(this) as List<CompanyProducts>)*.products as Set<Products>
    }

    boolean hasProducts(Products products)
    {
        return getProducts().contains(products)
    }

    Set<BarCode> getBarCodesOf(Products products)
    {
        if(!getProducts().contains(products)) return null
        Set<BarCode> barcodes = []

        for(barcode in barCodes)
        {
            if(products.getBarCodesOf(this).contains(barcode))
                barcodes.add(barcode)
        }
        return barcodes
    }


    static belongsTo = [user: User]
    static hasMany = [barCodes: BarCode]

    static constraints = {
        token nullable: false, blank: false, unique: true
        address nullable: false, blank: false
        companyId nullable: false, blank: false
    }
}
