package com.ttreport

import com.ttreport.datacenter.SigningService
import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest

class SigningServiceSpec extends HibernateSpec implements ServiceUnitTest<SigningService>{

    List<Class> getDomainClasses()
    {
        []
    }

    void "test something"() {
        expect:"fix me"
            true
    }
}
