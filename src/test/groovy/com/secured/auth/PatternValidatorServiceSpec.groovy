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
        boolean correct = service.validateUsername("normal_username")
        boolean wrong = service.validateUsername("1NCORRECT")
        then:
        correct == true
        wrong == false
    }

    void "test whitespace sensitivity"()
    {
        when:
        boolean correct = service.validateUsername("no_spaces")
        boolean incorrect = service.validateUsername(" space")
        boolean still_incorrect = service.validateUsername("space ")
        boolean not_correct_yet = service.validateUsername(" two spaces")
        boolean not_correct = service.validateUsername("a lot of spaces ")
        boolean still_not_correct = service.validateUsername(" a whole alotta spaces ")
        boolean otherwise_correct = service.validatePassword(" c0rr ecT1 ")
        then:
        correct == true
        incorrect == false
        still_incorrect == false
        not_correct == false
        still_not_correct == false
        not_correct_yet == false
        otherwise_correct == false
    }

}
