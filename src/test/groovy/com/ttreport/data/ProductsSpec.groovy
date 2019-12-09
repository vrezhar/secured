package com.ttreport.data

import com.ttreport.data.products.Products
import grails.test.hibernate.HibernateSpec
import grails.testing.gorm.DomainUnitTest

class ProductsSpec extends HibernateSpec implements DomainUnitTest<Products> {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
