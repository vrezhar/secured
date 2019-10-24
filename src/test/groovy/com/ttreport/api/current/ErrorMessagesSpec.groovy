package com.ttreport.api.current

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest
import grails.testing.spring.AutowiredTest
import org.grails.testing.GrailsUnitTest
import spock.lang.Specification

class ErrorMessagesSpec extends Specification implements AutowiredTest{

//    List<Class> getDomainClasses(){[]}

    Closure doWithSpring()
    {{->
        validationErrorResolverService(ValidationErrorResolverService)
     }
    }

    ValidationErrorResolverService validationErrorResolverService

    void setup()
    {
        assert validationErrorResolverService != null
    }

    void "test message code generation"()
    {
        expect:
            validationErrorResolverService.getCode("command.product.notfound") == 405
    }
}
