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
    }

    def confirm()
    {
        UserCommand cmd = UserCommand.createCommand(params)
        if(!cmd.validate() || !userValidator.isPasswordValid(cmd) || !userValidator.isUsernameValid(cmd))
        {
            cmd.password = ""
            errorCommand = cmd
            redirect(action: "register")
            //render view: "register",model: [user: cmd]
            return
        }
        if(userValidator.alreadyExists(params.username))
        {
            cmd.errors.rejectValue("username",
                    "user.username.exists")
            cmd.password = ""
            errorCommand = cmd
            redirect(action: "register")
            //render view: "register",model: [user: cmd]
            return
        }
        if (params.password != params.confirm)
        {
            cmd.errors.rejectValue("password",
                    "user.password.doesntmatch")
            cmd.password = ""
            errorCommand = cmd
            redirect(action: "register")
            //render view: "register",model: [user: cmd]
            return
        }

        User usr = User.createUser(cmd)
        def userRole = Role.findOrSaveWhere(authority: "ROLE_USER")
        usr.save()
        userInitializer.assignRole(usr,userRole,true)
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
        redirect controller: 'main',action:'confirm'
    }
}
