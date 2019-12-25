package com.ttreport.auth

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class PasswordRecoveryController {

    static scope = 'session'
    UserInitializerService userInitializer
    UserValidatorService userValidator
    SpringSecurityService springSecurityService

    def verify()
    {
        User usr = User.findWhere(mainToken: params.token)
        println(usr)
        if(!usr)
        {
            render view: '/notFound'
            return
        }
        render view: 'recoverPassword', model: [command: new PasswordRecoveryCommand(token: usr.mainToken)]
    }

    def validate(PasswordRecoveryCommand cmd)
    {
        println(cmd.password)
        println(cmd.errors)
        boolean haserrors = false
        if(!cmd.validate() ){
            haserrors = true
        }
        if(!userValidator.isRecoveredPasswordValid(cmd)) {
            haserrors = true
        }
        if(cmd.password != cmd.confirm){
            haserrors = true
            cmd.errors.rejectValue('confirm',"user.password.doesntmatch")
        }
        if(haserrors){
            withFormat {
                this.response.status = 400
                json{
                    respond([errors: cmd.errors, status: 400])
                }
            }
        }
        withFormat {
            this.response.status = 200
            json{
                respond([status: 200])
            }
        }
    }

    def recover(PasswordRecoveryCommand cmd)
    {
        String token = (cmd.token as String)?: params.token
        if(token == "don't"){
            render "don't"
            return
        }
        User user = User.findByMainToken(token)
        println(cmd.token)
        println(user?.dateCreated)
        println(cmd.password)
        println(cmd.confirm)
        if(!user){
            render view: '/notFound'
            return
        }
        if(userInitializer.updatePassword(user, cmd.password)){
            userInitializer.updateToken(user)
            springSecurityService.reauthenticate(user.username)
            flash.message = "password changed"
            redirect controller: 'user', action: 'profile'
            return
        }
        render view: '/internalServerError'
    }
}
