package com.secured.auth

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest

class PatternValidatorServiceSpec extends HibernateSpec implements ServiceUnitTest<PatternValidatorService>{


    void "test password size"() {
        when:"testing validation of various passwords"
            int forshort = service.validatePassword("short")
            int forlong = service.validatePassword("loremipsumdolorsitamet")

        then:"testing validation of various passwords"
            forshort == 0
            forlong == -1
    }
    void "test password strength"(){
        when:
        int weak = service.validatePassword("passwords")
        int solid = service.validatePassword("sup3rS3cr3t")
        then:
        weak == 1
        solid == 3
    }
    void "test username validity"(){
        when:
        boolean correct = service.validateUsername("noemal_username")
        boolean wrong = service.validateUsername("INCORRECT")
        then:
        correct == true
        wrong == false
    }
}
