package com.ttreport.data

import com.ttreport.auth.User
import com.ttreport.data.documents.differentiated.Document
import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode

@GrailsCompileStatic
@EqualsAndHashCode(includes='token')
class Company implements  Serializable
{

    private static final long serialVersionUID = 1

    String address
    String companyId
    String token = UUID.randomUUID().toString()

    Date dateCreated
    Date lastUpdated

    boolean hasProduct(Products products)
    {
        return this.products.contains(products)
    }

    boolean hasBarCode(BarCode barCode)
    {
        for(document in this.documents) {
            if(document.barCodes.contains(barCode)){
                return true
            }
        }
        return false
    }

    static belongsTo = [user: User]
    static hasMany = [products: Products, documents: Document]

    static constraints = {
        token nullable: false, blank: false, unique: true
        address nullable: false, blank: false
        companyId nullable: false, blank: false
    }
}
