package com.ttreport.data

import com.ttreport.api.resources.current.ProductCommand
import com.ttreport.logs.DevCycleLogger
import grails.compiler.GrailsCompileStatic
import groovy.transform.ToString

@GrailsCompileStatic
@ToString(cache=true, includes = ['code','dateCreated'], includePackage=false)
class BarCode implements Serializable
{

    private static final long serialVersionUID = 1

    String uit_code
    String uitu_code
    Date dateDeleted = null

    Date dateCreated
    Date lastUpdated

    static belongsTo = [products: Products]

    @Override
    boolean equals(Object o)
    {
        if(!(o instanceof BarCode)){
            return false
        }
        BarCode other = o as BarCode
        if(other.uitu_code == this.uitu_code && other.uit_code == this.uit_code){
            return true
        }
        return false
    }

    def transferOwnershipTo(Products other){
        this.products.barCodes.remove(this)
        this.products = other
        other.addToBarCodes(this)
    }

    static constraints = {
        dateDeleted nullable: true
        uit_code nullable: true, validator: { String value, BarCode object ->
            if(!value && !object?.uitu_code) {
                return false
            }
        }
        uitu_code nullable: true, validator: { String value, BarCode object ->
            if(!value && !object?.uit_code) {
                return false
            }
        }
    }
}
