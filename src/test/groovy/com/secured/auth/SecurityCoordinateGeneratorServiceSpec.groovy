package com.secured.auth

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

@SuppressWarnings('MethodName')
class SecurityCoordinateGeneratorServiceSpec extends HibernateSpec implements ServiceUnitTest<SecurityCoordinateGeneratorService>{



    void "test uniqueness of generated cards"() {
        when:
        def map = service.generateCoordinates()

        then:
        service.uniqueness(map) == 40

    }
    void "test card size"()
    {
        when:
        def map = service.generateCoordinates()
        then:
        map.size() == 40
    }
}
