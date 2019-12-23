package com.ttreport.data.products


import grails.compiler.GrailsCompileStatic
import groovy.transform.ToString

import java.util.regex.Matcher
import java.util.regex.Pattern

@GrailsCompileStatic
@ToString(cache=true, includes = ['code','dateCreated'], includePackage=false)
class BarCode implements Serializable
{

    private static final long serialVersionUID = 1L


    boolean inMarket = true
    String uitCode
    String uituCode
    Date dateDeleted = null

    Date dateCreated
    Date lastUpdated

    static belongsTo = [products: Products]
    CodeTailEncoded tail

    transient String getGTIN()
    {
        StringBuilder sb = new StringBuilder()
        String deconstructable = uitCode?: uituCode
        if(!deconstructable){
            return null
        }
        for(int i = 2; i < 16; ++i){
            sb.append(deconstructable[i])
        }
        return sb.toString()
    }

    transient String getSerialNumber()
    {
        StringBuilder sb = new StringBuilder()
        String deconstructable = uitCode?: uituCode
        if(!deconstructable){
            return null
        }
        for(int i = 16; i < 30; ++i){
            sb.append(deconstructable[i])
        }
        return sb.toString()
    }

    @Override
    boolean equals(Object o)
    {
        if(!(o instanceof BarCode)){
            return false
        }
        BarCode other = o as BarCode
        if(other?.uituCode == this.uituCode && other?.uitCode == this.uitCode && this.dateDeleted == other?.dateDeleted){
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
            if(value){
                Pattern pattern = Pattern.compile("01[0-9]{14}21[a-zA-Z0-9]{13}")
                Matcher matcher = pattern.matcher(value)
                if(!matcher.matches()){
                    return false
                }
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
