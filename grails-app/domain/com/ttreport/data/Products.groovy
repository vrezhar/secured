package com.ttreport.data

import com.ttreport.api.resources.current.ProductCommand
import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@GrailsCompileStatic
@EqualsAndHashCode(includes=['description', 'cost', 'tax'])
@ToString(cache=true, includes = ['description','dateCreated'], includePackage=false)
class Products implements Serializable
{
    private static final long serialVersionUID = 1

    String description
    int cost
    int tax

    Date dateCreated
    Date lastUpdated

    static  hasMany = [barCodes: BarCode]

    boolean hasBarcode(BarCode barCode)
    {
        return barCodes.contains(barCode)
    }

    static constraints = {
        description nullable: false, blank: false
        cost nullable: false, blank: false
        tax nullable: false, blank: false
    }

}
