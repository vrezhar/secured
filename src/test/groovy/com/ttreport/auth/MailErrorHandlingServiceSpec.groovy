package com.ttreport.auth

import com.ttreport.mail.MailErrorHandlingService
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class MailErrorHandlingServiceSpec extends Specification implements ServiceUnitTest<MailErrorHandlingService>{

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
