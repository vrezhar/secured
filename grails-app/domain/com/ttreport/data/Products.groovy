package com.ttreport.data


import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@GrailsCompileStatic
@EqualsAndHashCode(includes=['productCode'])
@ToString(cache=true, includes = ['productCode','description','dateCreated'], includePackage=false)
class Products implements Serializable
{
    private static final long serialVersionUID = 1

    String productCode
    String description
    int cost
    int tax
    Date dateCreated
    Date lastUpdated

    static  hasMany = [barCodes: BarCode]
    static belongsTo = [company: Company]

    static constraints = {
        productCode nullable: false, blank: false, unique: true
        description nullable: false, blank: false
        cost nullable: false, blank: false
        tax nullable: false, blank: false
    }

}