package com.secured.auth

import com.secured.Mail
import com.secured.user.UserCommand
import com.secured.strategy.handlers.RejectEmail
import com.secured.strategy.senders.SendViaGmail
import grails.plugin.springsecurity.annotation.Secured

@Secured(["permitAll"])
class RegisterController
{

    UserInitializerService userInitializer
    UserValidatorService userValidator
    private static UserCommand errorCommand

    def register()
    {
        UserCommand usercommand = errorCommand ?: new UserCommand()
        render view: 'register', model: [user: usercommand]
        errorCommand = null
    }

    def confirm()
    {
        UserCommand cmd = UserCommand.createCommand(params)
        boolean haserrors = false
        if(!cmd.validate() )
            haserrors = true
        if(!userValidator.isPasswordValid(cmd))
            haserrors = true
        if(!userValidator.isUsernameValid(cmd))
            haserrors = true
        if(userValidator.alreadyExists(cmd))
            haserrors = true
        if(params.confirm == null || params.confirm == "" || params.password != params.confirm)
        {
            cmd.errors.rejectValue("password",
                    "user.password.doesntmatch")
            haserrors = true
        }
        if(haserrors)
        {
            cmd.password = ""
            errorCommand = cmd
            //render view: "register", model: [user: cmd]
            redirect(action: "register")
            return
        }
        User usr = User.createUser(cmd)
        def userRole = Role.findOrSaveWhere(authority: "ROLE_USER")
        userInitializer.assignRole(usr,userRole)
        String message = "Click the link below to verify your email\n " +
                "localhost:8080/verify?token=${usr.mainToken}"
        new Mail()
                .from("your email")
                .to(usr.email)
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
