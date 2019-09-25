package com.secured.data

import com.secured.auth.User

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

    boolean hasProducts(Products products)
    {
        return this.products.contains(products)
    }

    static belongsTo = [user: User]
    static hasMany = [products: Products]

    static constraints = {
        token nullable: false, blank: false, unique: true
        address nullable: false, blank: false
        companyId nullable: false, blank: false
    }
}
