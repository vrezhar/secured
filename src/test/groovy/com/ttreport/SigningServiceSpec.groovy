package com.ttreport

import com.ttreport.datacentre.SigningService
import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest

class SigningServiceSpec extends HibernateSpec implements ServiceUnitTest<SigningService>{

    List<Class> getDomainClasses()
    {
        []
    }

    void "test something"() {
        service.sign("".getBytes(),true)
        expect:"fix me"
            true
    }
}
