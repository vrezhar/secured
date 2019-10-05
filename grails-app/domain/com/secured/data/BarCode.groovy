package com.secured.data


import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@GrailsCompileStatic
@ToString(cache=true, includes = ['code','dateCreated'], includePackage=false)
class BarCode implements Serializable
{

    private static final long serialVersionUID = 1


    String uit_code
    String uitu_code
    Date dateCreated
    Date lastUpdated
    Date dateDeleted = null


    static belongsTo = [products: Products]

    static constraints = {
        dateDeleted nullable: true
        uit_code nullable: true, validator: { String value, BarCode object ->
            if(value && BarCode.findWhere(uit_code: value)) {
                return false
            }
            if(!object?.uitu_code  && !value) {
                return false
            }
        }
        uitu_code nullable: true, validator: { String value, BarCode object ->
            if(value && BarCode.findWhere(uitu_code: value)) {
                return false
            }
            if(!object?.uit_code && !value) {
                return false
            }
        }
    }
}
