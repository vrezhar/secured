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
        String[] files = ["/home/vrezh/grails/ttreport/Certificates/tensor.crt","/home/vrezh/grails/ttreport/Certificates/CA.crt"]
        println(files)
        service.sign("a".getBytes(),files)
        expect:"fix me"
            true
    }
}
