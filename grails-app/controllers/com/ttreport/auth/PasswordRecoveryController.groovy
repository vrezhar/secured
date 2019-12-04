package com.ttreport.auth

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class PasswordRecoveryController {

    UserInitializerService userInitializer
    UserValidatorService userValidator
    SpringSecurityService springSecurityService

    def verify()
    {
        User usr = User.findWhere(mainToken: params.token)
        if(usr)
        {
            userInitializer.updateToken(usr)
            flash.redirect = true
            flash.user = usr
            render view: 'recoverPassword'
            return
        }
        render view: '/notFound'
    }

    def validate(PasswordRecoveryCommand cmd)
    {
        boolean haserrors = false
        println(request.reader.toString())
        if(!cmd.validate() ){
            haserrors = true
        }
        if(!userValidator.isRecoveredPasswordValid(cmd)) {
            haserrors = true
        }
        if(cmd.password != cmd.confirm){
            haserrors = true
        }
        return !haserrors
    }

    def recover(PasswordRecoveryCommand cmd)
    {
        if(!flash.redirect){
            render view: 'notFound'
            return
        }
        User user = flash.user as User
        if(userInitializer.updatePassword(user, cmd.password)){
            springSecurityService.reauthenticate(user.username,user.password)
            flash.message = "password changed"
            redirect controller: 'user', action: 'profile'
            return
        }
        render view: '/internalServerError'
    }
}
