package com.ttreport.auth

import com.ttreport.logs.ServerLogger
import com.ttreport.user.UserCommand
import grails.gorm.transactions.Transactional


@Transactional
class UserValidatorService extends PatternValidatorService
{

    static scope = 'prototype'

    def isPasswordValid(UserCommand usr)
    {
        ServerLogger.log("validator called")
        if(usr == null || !usr.password) {
            return false
        }

        switch(validatePassword(usr.password)) {
            case -2:
                ServerLogger.log("password is too short")
                usr.errors.rejectValue("password","user.password.too.short")
                return false
            case -1:
                ServerLogger.log("password is too long")
                usr.errors.rejectValue("password","user.password.too.long")
                return false
            case 0:
                ServerLogger.log("password contains whitespaces")
                usr.errors.rejectValue("password","user.password.whitespaces")
                return false
            case 1:
                ServerLogger.log("password is too weak")
                usr.errors.rejectValue("password","user.password.too.weak")
                return false
        }
        return true
    }

    def isRecoveredPasswordValid(PasswordRecoveryCommand cmd)
    {
        ServerLogger.log("validator called")
        if(cmd == null || !cmd.password) {
            return false
        }

        switch(validatePassword(cmd.password)) {
            case -2:
                ServerLogger.log("password is too short")
                cmd.errors.rejectValue("password","user.password.too.short")
                return false
            case -1:
                ServerLogger.log("password is too long")
                cmd.errors.rejectValue("password","user.password.too.long")
                return false
            case 0:
                ServerLogger.log("password contains whitespaces")
                cmd.errors.rejectValue("password","user.password.whitespaces")
                return false
            case 1:
                ServerLogger.log("password is too weak")
                cmd.errors.rejectValue("password","user.password.too.weak")
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
