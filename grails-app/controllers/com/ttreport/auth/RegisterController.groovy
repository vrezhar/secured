package com.ttreport.auth

import com.ttreport.mail.Mail
import com.ttreport.logs.ServerLogger
import com.ttreport.user.UserCommand
import com.ttreport.mail.strategy.handlers.RejectEmail
import com.ttreport.mail.strategy.senders.SendViaGmail
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

@Secured(["permitAll"])
class RegisterController
{

    static defaultAction = "register"

    UserInitializerService userInitializer
    UserValidatorService userValidator
    SpringSecurityService springSecurityService
    private UserCommand errorCommand

    def register()
    {
        if(springSecurityService.isLoggedIn()){
            redirect controller: "user", action: "profile"
            return
        }
        UserCommand usercommand = errorCommand ?: new UserCommand()
        render view: 'register', model: [user: usercommand]
        errorCommand = null
    }

    def validate(UserCommand cmd)
    {
        boolean haserrors = false
        if(!cmd.validate() ){
            haserrors = true
        }
        if(!userValidator.isPasswordValid(cmd)) {
            haserrors = true
        }
        if(userValidator.alreadyExists(cmd)){
            haserrors = true
        }
        if(cmd.password != cmd.confirm) {
            cmd.errors.rejectValue("confirm",
                    "user.password.doesntmatch")
            haserrors = true
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

    def confirm(UserCommand cmd)
    {
        boolean haserrors = false
        if(!cmd.validate() )
            haserrors = true
        if(!userValidator.isPasswordValid(cmd))
            haserrors = true
        if(userValidator.alreadyExists(cmd))
            haserrors = true
        if(!cmd.confirm || cmd.password != cmd.confirm)
        {
            cmd.errors.rejectValue("password",
                    "user.password.doesntmatch")
            haserrors = true
        }
        if(haserrors)
        {
            ServerLogger.log_validation_errors(cmd)
            cmd.password = ""
            cmd.confirm = ""
            errorCommand = cmd
            redirect(action: "register")
            ServerLogger.print_logs()
            ServerLogger.cleanup()
            return
        }
        User usr = User.createUser(cmd)
        def userRole = Role.findOrSaveWhere(authority: "ROLE_USER")
        userInitializer.assignRole(usr,userRole)
        String message = "<html>" +
                "<br>Dear ${usr.firstName} ${usr.lastName}<br>"+
                "<span></span>" +
                "<p>You have registered in TTReport using this email address.</p>" +
                "<p>Click on the link below to verify your email.</p>" +
                "<a href = 'www.ttreport.ru/verify?token=${usr.mainToken}'>www.ttreport.ru/verify?token=${usr.mainToken}</a>" +
                "<p>If you didn't register in ttreport.ru just ignore this message</p>" +
                "<span></span>" +
                "<p>Sincerely\n" +
                "<br>TTReport team\n</br>" +
                "<a>www.ttreport.ru</a></p>" +
                "</html>"
        new Mail()
                .to(usr.username)
                .withSubject("Confirm your email")
                .withMessage(message)
                .useSendingStrategy(SendViaGmail.usingDefaultAccount())
                .onErrors(RejectEmail.withDefaultMessage())
                .send()
                .printConfiguration()
        println(message)
        flash.message = "pending"
        redirect controller: 'main',action:'confirm'
    }
}
