package com.secured.auth

import com.secured.user.UserCommand
import grails.gorm.transactions.Transactional


@Transactional
class UserValidatorService extends PatternValidatorService {

    def isPasswordValid(UserCommand usr)
    {

        if(usr == null || usr.password == "" || usr.password == null)
            return false

        switch(validatePassword(usr.password))
        {
            case -2:
                usr.errors.rejectValue("password","user.password.tooshort")
                return false
            case -1:
                usr.errors.rejectValue("password","user.password.toolong")
                return false
            case 0:
                usr.errors.rejectValue("password","user.password.whitespaces")
                return false
            case 1:
                usr.errors.rejectValue("password","user.password.tooweak")
                return false
        }
        return true
    }

    def isUsernameValid(UserCommand usr)
    {
        if(usr == null || usr.username == "" || usr.username == null)
            return false
        if(!validateUsername(usr.username))
        {
            usr.errors.rejectValue("username","user.username.incorrect")
            return false
        }
        return true
    }

    def alreadyExists(UserCommand cmd)
    {
        if(cmd == null)
            return false
        User usr = User.findWhere(username: cmd.username)
        if(!usr)
            return false
        cmd.errors.rejectValue("password",
                "user.password.doesntmatch")
        return true
    }

}
