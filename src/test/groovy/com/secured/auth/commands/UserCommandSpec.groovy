package com.secured.auth.commands

import com.secured.user.UserCommand
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class UserCommandSpec extends Specification implements DomainUnitTest<UserCommand> {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
