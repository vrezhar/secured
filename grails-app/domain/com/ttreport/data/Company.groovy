package com.ttreport.data

import com.ttreport.auth.User
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.data.products.BarCode
import com.ttreport.data.products.Products
import grails.compiler.GrailsCompileStatic
import groovy.transform.EqualsAndHashCode

@GrailsCompileStatic
@EqualsAndHashCode(includes='token')
class Company implements  Serializable
{

    private static final long serialVersionUID = 3L

    String address = "Terra"
    String inn = "000052520"
    String name = "OOO TEST"
    String omsToken = "from oms"
    String omsId = "from oms"
    String token = UUID.randomUUID().toString()
    String contactPerson
    final transient Object orderMonitor = new Object()

    Date dateCreated
    Date lastUpdated

    boolean hasProduct(Products products)
    {
        return !products ? false : this.products?.contains(products)
    }

    boolean hasBarCode(BarCode barCode)
    {
        if(barCode?.dateDeleted){
            return false
        }
        for(product in products) {
            if(product.barCodes?.contains(barCode)){
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
        inn nullable: false, blank: false
        name nullable: false, blank: false
        contactPerson nullable: true, blank: false
    }
}
