package com.ttreport.data

import com.ttreport.auth.Role
import com.ttreport.auth.User
import com.ttreport.auth.UserRole
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.data.documents.differentiated.existing.MarketEntranceDocument
import com.ttreport.data.products.BarCode
import com.ttreport.data.products.CodeTailEncoded
import com.ttreport.data.products.MarketEntranceBarCode
import com.ttreport.data.products.Products
import grails.test.hibernate.HibernateSpec
import grails.testing.gorm.DomainUnitTest

class ProductsSpec extends HibernateSpec
{

    List<Class> getDomainClasses()
    {
        [BarCode, Products, CodeTailEncoded]
    }


    void "test finding by gtin"() {
        CodeTailEncoded tail = new CodeTailEncoded(encodedTail: "test")
        Products products = new Products(description: "test", cost: 100, tax: 10)
        BarCode barCode = new BarCode(uitCode: "010460084036212021QIQ8BQCX9SJJe", products: products, tail: tail)
        products.addToBarCodes(barCode)
        products.save()
        tail.save()
        barCode.save()
        println(barCode.getGTIN())
        expect:
            true
    }
}
