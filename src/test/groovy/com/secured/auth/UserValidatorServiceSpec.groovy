package com.secured.auth

import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

//Note to self: Services are singleton, don't inject them in each other
//and inject BOTH of them in the same place
//TODO fix this
class UserValidatorServiceSpec extends HibernateSpec implements ServiceUnitTest<UserValidatorService>{

    List<Class> getDomainClasses()
    {
        [User, UserPasswordEncoderListener, PatternValidatorService]
    }

    void "test password validation"() {
        when:
        User usr = new User(password: "wrong",username: "test",
                firstName: "test",lastName: "test",
                email: "test@test.com")
        User other_usr = new User(password: "Password1",username: "test",
                firstName: "test",lastName: "test",
                email: "test@test.com")
        User short_user = new User(password: "Notit",username: "test",
                firstName: "test",lastName: "test",
                email: "test@test.com")
        User another_usr = new User(password: "password1",username: "test",
                firstName: "test",lastName: "test",
                email: "test@test.com")
        then:
        service.isPasswordValid(usr) == false
        service.isPasswordValid(other_usr) == true
        service.isPasswordValid(another_usr) == true
        service.isPasswordValid(short_user) == false

    }

    void "test username validation"()
    {
        when:
        User usr = new User(password: "password1",username: "username",
                firstName: "test",lastName: "test",
                email: "test@test.com")
        User other_usr = new User(password: "password1",username: "5tillwrong",
                firstName: "test",lastName: "test",
                email: "test@test.com")
        User another_user = new User(password: "password1",
                username: "morewrongthanyoucanimagine",
                firstName: "test",lastName: "test",
                email: "test@test.com")
        User correct = new User(password: "password1",username: "test",
                firstName: "test",lastName: "test",
                email: "test@test.com")
        then:
        service.isUsernameValid(usr) == true
        service.isUsernameValid(other_usr) == false
        service.isUsernameValid(another_user) == false
        service.isUsernameValid(correct) == true
    }

    void "test existence validation"()
    {
        when:
        User usr = new User(username: "username", password: "iamC0rrect",
                            firstName: "myname", lastName: "jeff",
                            email: "jeff@jeff.com")
        //Note to self: domain.save() won't work if the domain instance is invalid
        usr.save(true)
        then:
        println(usr.username)
        service.alreadyExists("username") == true
    }

    void "test general validation"()
    {
        when:
        User absent_username = new  User(password: "password1",
                firstName: "test",lastName: "test",
                email: "test@test.com")
        User absent_password = new  User(username: "test",
                firstName: "test",lastName: "test",
                email: "test@test.com")
        User absent_firstName = new  User(password: "password1",username: "test",
                lastName: "test",
                email: "test@test.com")
        User absent_last_name = new  User(password: "password1",username: "test",
                firstName: "test",
                email: "test@test.com")
        User absent_email = new  User(password: "password1",username: "test",
                firstName: "test",lastName: "test")
        then:
        service.isValid(absent_email) == false
        service.isValid(absent_firstName) == false
        service.isValid(absent_last_name) == false
        service.isValid(absent_password) == false
        service.isValid(absent_username) == false
    }

}
