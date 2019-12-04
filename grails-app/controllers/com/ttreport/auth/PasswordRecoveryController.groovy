package com.ttreport.auth

import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class PasswordRecoveryController {

    UserInitializerService userInitializerService
    UserValidatorService userValidator

    def verify()
    {
        User usr = User.findWhere(mainToken: params.token)
        if(usr)
        {
            userInitializerService.updateToken(usr)
            flash.redirect = true
            flash.user = usr
            render view: 'recoverPassword'
            return
        }
        render view: '/notFound'
    }

    def validate(PasswordRecoveryCommand cmd)
    {
        boolean haserrors
        if(!cmd.validate() ){
            haserrors = true
        }
        if(!userValidator.isRecoveredPasswordValid(cmd)) {
            haserrors = true
        }
    }

    def recover(PasswordRecoveryCommand cmd)
    {
        if(!flash.redirect){
            render view: 'notFound'
            return
        }
        User user = flash.user as User
    }
}
