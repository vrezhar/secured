package com.secured.api

import com.secured.api.current.ProductsService
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class ProductsServiceSpec extends Specification implements ServiceUnitTest<ProductsService>{

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
