package com.secured.auth

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured


class UserController  {

    UserInitializerService userInitializer
    UserValidatorService userValidator
    SpringSecurityService springSecurityService
    TokenGeneratorService tokenGeneratorService

    @Secured(['permitAll'])
    def register()
    {
        if(request.method == 'POST')
        {

            if(userValidator.alreadyExists(params.username))
            {
                def usr = new User(username: params.username,
                                   firstName: params.firstName,
                                   lastName: params.lastName,
                                   email: params.email)
                usr.errors.rejectValue("username",
                                   "user.username.exists")
                render view: "register", model: [user: usr]
                return
            }
            User usr = new User(username: params.username,
                                password: params.password,
                                firstName: params.firstName,
                                lastName: params.lastName,
                                email: params.email)
            def userRole = Role.findOrSaveWhere(authority: "ROLE_USER")
            if (params.password != params.confirm)
            {
                usr.errors.rejectValue("password",
                                       "user.password.doesntmatch")
                render view:"register", model: [user: usr]
                return
            }

            if(userValidator.isValid(usr) && userValidator.isUsernameValid(usr) && userValidator.isPasswordValid(usr))
            {
                usr.mainToken = tokenGeneratorService.generate(usr)
                userInitializer.assignRole(usr,userRole,true)


                String message = "Click the link below to verify your email\n localhost:8080/verify?token=${usr.mainToken}"
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
            render view:"register", model: [user: usr]
        }

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
