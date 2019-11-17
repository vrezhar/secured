package com.ttreport.data


import grails.compiler.GrailsCompileStatic
import groovy.transform.ToString

@GrailsCompileStatic
@ToString(cache=true, includes = ['code','dateCreated'], includePackage=false)
class BarCode implements Serializable
{

    private static final long serialVersionUID = 1
    boolean minified = false

    String uitCode
    String uituCode
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
        if(other.uituCode == this.uituCode && other.uitCode == this.uitCode){
            return true
        }
        return false
    }

    static constraints = {
        dateDeleted nullable: true
        uitCode nullable: true, validator: { String value, BarCode object ->
            if(!value && !object?.uituCode) {
                return false
            }
        }
        uituCode nullable: true, validator: { String value, BarCode object ->
            if(!value && !object?.uitCode) {
                return false
            }
        }
        products nullable: true
    }
    static mapping = {
        tablePerHierarchy false
    }
}
