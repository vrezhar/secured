package com.secured.data


import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@GrailsCompileStatic
@ToString(cache=true, includeNames=true, includePackage=false)
class BarCode implements Serializable
{

    private static final long serialVersionUID = 1
    //ToDo
    //Implement this properly later
    String code
    Date dateCreated
    Date lastUpdated
    Date dateDeleted = null

    @Override
    boolean equals(Object o)
    {
        if(o instanceof BarCode)
        {
            BarCode other = o as BarCode
            if(this.code == other.code && (this.company.companyId == other.company.companyId))
                return true
        }
        return false
    }

    static belongsTo = [company: Company]

    static constraints = {
        dateDeleted nullable: true
    }
}
