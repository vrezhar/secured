package com.ttreport

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class Base64EncoderServiceSpec extends HibernateSpec implements ServiceUnitTest<Base64EncoderService>{

    List<Class> getDomainClasses()
    {
        []
    }

    void "test something"() {
        service.sign("VGYPFVXUQDWSIMRTGIBKHLMVMQMMJM")
        expect:"fix me"
            true
    }
}
