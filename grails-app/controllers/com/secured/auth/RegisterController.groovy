package com.secured.auth

import com.secured.Mail
import com.secured.logs.DevCycleLogger
import com.secured.user.UserCommand
import com.secured.strategy.handlers.RejectEmail
import com.secured.strategy.senders.SendViaGmail
import grails.plugin.springsecurity.annotation.Secured

@Secured(["permitAll"])
class RegisterController
{

    UserInitializerService userInitializer
    UserValidatorService userValidator
    private UserCommand errorCommand

    def register()
    {
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
            DevCycleLogger.log_validation_errors(cmd)
            cmd.password = ""
            cmd.confirm = ""
            errorCommand = cmd
            redirect(action: "register")
            DevCycleLogger.print_logs()
            DevCycleLogger.cleanup()
            return
        }
        User usr = User.createUser(cmd)
        def userRole = Role.findOrSaveWhere(authority: "ROLE_USER")
        userInitializer.assignRole(usr,userRole)
        String message = "Click the link below to verify your email\n " +
                "localhost:8080/verify?token=${usr.mainToken}"
        new Mail()
                .from("your email")
                .to(usr.username)
                .withSubject("Confirm your email")
                .withMessage(message)
                .useSendingStrategy(SendViaGmail.usingAccount("bronsmailsupreme@gmail.com",
                        "bruhMoment"))
                .onErrors(RejectEmail.withMessage())
                .send()
        println(message)
        redirect controller: 'main',action:'confirm'
    }
}
