package com.secured.auth

import com.secured.Mail
import com.secured.strategy.handlers.RejectEmail
import com.secured.strategy.senders.SendViaGmail
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured


class UserController  {

    UserInitializerService userInitializer
    UserValidatorService userValidator
    SpringSecurityService springSecurityService

    @Secured(['permitAll'])
    def register()
    {
        UserCommand usercommand = flash.model as UserCommand ?: new UserCommand()
        render view: 'register', model: [user: usercommand]
    }

    @Secured(['permitAll'])
    def confirm()
    {
        UserCommand cmd = UserCommand.createCommand(params)
        if(userValidator.alreadyExists(params.username))
        {
            cmd.errors.rejectValue("username",
                               "user.username.exists")
            flash.model = cmd
            redirect(action:"register")
            return
        }
        if (params.password != params.confirm)
        {
            cmd.errors.rejectValue("password",
                               "user.password.doesntmatch")
            flash.model = cmd
            redirect(action:"register")
            return
        }
        if(cmd.validate())
        {
            User usr = User.createUser(cmd)
            def userRole = Role.findOrSaveWhere(authority: "ROLE_USER")
            if(userValidator.isUsernameValid(usr)  && userValidator.isPasswordValid(usr))
            {
                usr.save()
                userInitializer.assignRole(usr,userRole,true)
                String message = "Click the link below to verify your email\n " +
                        "localhost:8080/verify?token=${usr.mainToken}"
                new Mail()
                    .from("your email")
                    .to(usr.email)
                    .withSubject("Confirm your email")
                    .withMessage(message)
                    .useSendingStrategy(SendViaGmail.usingAccount("your username",
                                "your password"))
                    .onErrors(RejectEmail.withMessage("Something went wrong"))
                    .send()
                springSecurityService.reauthenticate(usr.username,usr.password)
                redirect controller: 'main',action:'confirm'
                return
            }
        }
        flash.model = cmd
        redirect(action:"register")

    }


    @Secured(['ROLE_USER','ROLE_ADMIN'])
    def show(long id)
    {
        User user = User.findById(id)
        if(user) {
            render model: [user: user], view: "details"
            return
        }
        render model: [user: springSecurityService.getCurrentUser()], view: "details"
    }

}
