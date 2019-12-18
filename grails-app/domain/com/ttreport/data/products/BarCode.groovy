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

    String uitCode
    String uituCode
    Date dateDeleted = null

    Date dateCreated
    Date lastUpdated

    static belongsTo = [products: Products]
    static hasOne = [tail: CodeTailEncoded]

    transient String getGTIN()
    {
        return Arrays.copyOfRange(uitCode?.toCharArray()?: uituCode?.toCharArray(), 2, 16)
    }

    transient String getSerialNumber()
    {
        char[] code = uitCode?.toCharArray()?: uituCode?.toCharArray()
        return Arrays.copyOfRange(code, 17, code.size() - 1)
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
                Pattern pattern = Pattern.compile("01\\[0-9]\\{14}21\\[a-zA-Z0-9]\\{13}")
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
            if(value){
                Pattern pattern = Pattern.compile("01\\[0-9]\\{14}21\\[a-zA-Z0-9]\\{13}")
                Matcher matcher = pattern.matcher(value)
                if(!matcher.matches()){
                    return false
                }
            }
        }
        products nullable: true
    }
    static mapping = {
        tablePerHierarchy false
    }
}
