package com.ttreport.auth

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest

//TODO fix this
class UserInitializerServiceSpec extends HibernateSpec implements ServiceUnitTest<UserInitializerService> {

    List<Class> getDomainClasses() {
        [User, Role, UserRole, UserPasswordEncoderListener]
    }


    void "test role assignment by Role object"() {
        when:
        def test_role = Role.findOrSaveWhere(authority: "ROLE_TEST")
        def user = new User(firstName: "bron", lastName: "bronson",
                username: "bruh", password: "bruhMoment", email:"test@test.com"
        )
        //Note to self: save something before searching for them in a database
        service.assignRoleAndSave(user, test_role,true)
        then:
        UserRole.findByUser(user).role.authority == "ROLE_TEST"
    }

    void "test role assignment by string"() {
        when:
        def test_role = "ROLE_TEST"
        def user = new User(firstName: "bron", lastName: "bronson",
                username: "bruh", password: "bruhMoment",email: "test@test.com"
        )
        service.assignRoleAndSave(user, test_role)

        then:
        UserRole.findByUser(user).role.authority == "ROLE_TEST"
    }


}