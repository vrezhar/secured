package com.secured.data

import com.secured.logs.DevCycleLogger
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
    Date dateDeleted = null

    Date dateCreated
    Date lastUpdated



    static belongsTo = [products: Products]

    static constraints = {
        dateDeleted nullable: true
        uit_code nullable: true, validator: { String value, BarCode object ->
            if(value && (findWhere(uitu_code: value))) {
                DevCycleLogger.log("inside uit's validator, first if")
                return false
            }
            if(!object?.uitu_code  && !value && !object?.uit_code) {
                DevCycleLogger.log("inside uit's validator, second if")
                return false
            }
        }
        uitu_code nullable: true, validator: { String value, BarCode object ->
            if(value && (findWhere(uitu_code: value))) {
                DevCycleLogger.log("inside uitu's validator, first if")
                return false
            }
            if(!object?.uit_code && !value && !object?.uitu_code) {
                DevCycleLogger.log("inside uitu's validator, second if")
                return false
            }
        }
    }
}
