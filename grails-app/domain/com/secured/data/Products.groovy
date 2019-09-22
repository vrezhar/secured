package com.secured.data

import com.secured.data.connectors.ProductsBarcodes
import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@GrailsCompileStatic
@EqualsAndHashCode(includes='productCode')
@ToString(cache=true, includeNames=true, includePackage=false)
class Products implements Serializable
{
    private static final long serialVersionUID = 1

    String productCode
    String description
    Date dateCreated
    Date lastUpdated

    Set<BarCode> getAllBarCodes()
    {
        (ProductsBarcodes.findAllByProducts(this) as List<ProductsBarcodes>)*.barcode as Set<BarCode>
    }

    Set<BarCode> getBarCodesOf(Company owner)
    {
        Set<BarCode> barcodes = []
        for(barcode in getAllBarCodes())
        {
            if(barcode.company == owner)
                barcodes.add(barcode)
        }
        return barcodes
    }

    static constraints = {
        productCode nullable: false, blank: false
        description nullable: true, blank: true
    }

}
