package com.ttreport.api.current

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class ValidationErrorResolverServiceSpec extends HibernateSpec implements ServiceUnitTest<ValidationErrorResolverService>{

    void "test something"() {
        def message = service.getMessage('command.product.notfound')
        println(message)
        expect:"fix me"
            message == 'Product not found'
    }
}
