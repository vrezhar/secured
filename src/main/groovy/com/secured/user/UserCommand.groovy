package com.secured.user

import grails.validation.Validateable


class UserCommand implements Validateable
{

    String username
    String password
    String firstName
    String lastName
    String email

    static UserCommand createCommand(Map map)
    {
        UserCommand cmd = new UserCommand()
        cmd.username = map.username
        cmd.password = map.password
        cmd.email = map.email
        cmd.firstName = map.firstName
        cmd.lastName = map.lastName
        return cmd
    }

    static constraints = {
        password nullable: false, blank: false, password: true
        username nullable: false, blank: false, unique: true
        firstName nullable: false, blank: false
        lastName nullable: false, blank: false
        email nullable: false,blank: false,email: true,unique: true
    }
}
