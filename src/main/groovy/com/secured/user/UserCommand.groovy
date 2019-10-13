package com.secured.user

import com.secured.auth.User
import com.secured.auth.UserValidatorService
import grails.validation.Validateable


class UserCommand implements Validateable
{

    String username
    String password
    String confirm
    String firstName
    String lastName

    static UserCommand createCommand(Map map)
    {
        UserCommand cmd = new UserCommand()
        cmd.username = map.username
        cmd.password = map.password
        cmd.firstName = map.firstName
        cmd.lastName = map.lastName
        return cmd
    }

    static constraints = {
        password nullable: false, blank: false
        username nullable: false, blank: false, email: true
        firstName nullable: false, blank: false
        lastName nullable: false, blank: false
        confirm nullable: false, blank: false
    }
}
