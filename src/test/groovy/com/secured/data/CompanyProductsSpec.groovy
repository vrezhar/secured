package com.secured.data

import com.secured.data.connectors.CompanyProducts
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class CompanyProductsSpec extends Specification implements DomainUnitTest<CompanyProducts> {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
