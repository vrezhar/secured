package com.ttreport.auth

import com.ttreport.logs.DevCycleLogger
import com.ttreport.user.UserCommand
import grails.gorm.transactions.Transactional


@Transactional
class UserValidatorService extends PatternValidatorService {

    def isPasswordValid(UserCommand usr)
    {
        DevCycleLogger.log("validator called")
        if(usr == null || !usr.password) {
            return false
        }

        switch(validatePassword(usr.password)) {
            case -2:
                DevCycleLogger.log("password is too short")
                usr.errors.rejectValue("password","user.password.tooshort")
                return false
            case -1:
                DevCycleLogger.log("password is too long")
                usr.errors.rejectValue("password","user.password.toolong")
                return false
            case 0:
                DevCycleLogger.log("password contains whitespaces")
                usr.errors.rejectValue("password","user.password.whitespaces")
                return false
            case 1:
                DevCycleLogger.log("password is too weak")
                usr.errors.rejectValue("password","user.password.tooweak")
                return false
        }
        return true
    }

    def isUsernameValid(UserCommand usr)
    {
        if(usr == null || !usr.username)
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
        cmd.errors.rejectValue("username",
                "user.username.exists")
        return true
    }

}
