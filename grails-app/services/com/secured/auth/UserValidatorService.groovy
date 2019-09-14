package com.secured.auth

import com.secured.auth.commands.UserCommand
import grails.gorm.transactions.Transactional


@Transactional
class UserValidatorService extends PatternValidatorService {

    def isPasswordValid(UserCommand usr)
    {

        if(usr == null)
            return false

        switch(validatePassword(usr.password))
        {
            case -2:
                usr.errors.rejectValue("password","user.password.tooshort")
                usr.password = ""
                return false
            case -1:
                usr.errors.rejectValue("password","user.password.toolong")
                usr.password = ""
                return false
            case 0:
                usr.errors.rejectValue("password","user.password.whitespaces")
                usr.password = ""
                return false
            case 1:
                usr.errors.rejectValue("password","user.password.tooweak")
                usr.password = ""
                return false
        }
        return true
    }

    def isUsernameValid(UserCommand usr)
    {
        if(usr == null)
            return false
        if(!validateUsername(usr.username))
        {
            usr.errors.rejectValue("username","user.username.incorrect")
            return false
        }
        return true
    }

    def alreadyExists(String username)
    {
        if(username == null)
            return false
        User usr = User.findWhere(username: username)
        if(usr)
            return true
        return false
    }
}
