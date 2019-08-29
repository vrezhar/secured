package com.secured.auth

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class UserInitializerServiceSpec extends HibernateSpec implements ServiceUnitTest<UserInitializerService> {

    List<Class> getDomainClasses() {
        [User, Role, UserRole, UserPasswordEncoderListener]
    }


    void "test role assignment by Role object"() {
        when:
        def test_role = Role.findOrSaveWhere(authority: "ROLE_TEST")
        def user = new User(firstName: "bron", lastName: "bronson",
                username: "bruh", password: "bruhMoment"
        )
        service.assignRole(user, test_role,true)
        then:
        UserRole.findByUser(user).role.authority == "ROLE_TEST"
    }

    void "test role assignment by string"() {
        when:
        def test_role = "ROLE_TEST"
        def user = new User(firstName: "bron", lastName: "bronson",
                username: "bruh", password: "bruhMoment"
        )
        service.assignRole(user, test_role)
        then:
        UserRole.findByUser(user).role.authority == "ROLE_TEST"
    }

    void "test security card initialization"() {
        when:

        def mockCard = ["A1": "10"]
        def test_role = "ROLE_TEST"

        def user = new User(firstName: "bron", lastName: "bronson",
                username: "bruh", password: "bruhMoment"
        )
        service.assignRole(user, test_role)
        service.addToCoordinates(user, mockCard)
        then:
        UserRole.findByUser(user).user.coordinates[0].value == "10"

    }

}