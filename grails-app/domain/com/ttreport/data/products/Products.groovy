package com.ttreport.data.products

import com.ttreport.data.Company
import com.ttreport.data.products.BarCode
import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@GrailsCompileStatic
@EqualsAndHashCode(includes=['description', 'cost', 'tax', 'company'])
@ToString(cache=true, includes = ['description','dateCreated'], includePackage=false)
class Products implements Serializable
{
    private static final long serialVersionUID = 1

    String description
    int cost = 0
    int tax = 0

    Date dateCreated
    Date lastUpdated

    static  hasMany = [barCodes: BarCode]
    static belongsTo = [company: Company]

    boolean hasBarcode(BarCode barCode)
    {
        return barCodes.contains(barCode)
    }

    static transient Products findByGTIN(String gtin)
    {
        for(products in Products.list()){
            for(barCode in products.barCodes){
                if(barCode.getGTIN() == gtin){
                    return products
                }
                break
            }
        }
        return null
    }

    static constraints = {
        description nullable: true, blank: true
        barCodes nullable: true
        company nullable: true //TODO investigate this more thoroughly
    }

    static mapping = {
        tablePerHierarchy false
    }
}
