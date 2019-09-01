package com.secured.auth


import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class UserController  {

    UserInitializerService userInitializer
    UserValidatorService userValidator
    def springSecurityService

    def register()
    {

        if(request.method == 'POST')
        {

            if(userValidator.alreadyExists(params.username))
            {
                def usr = new User(username: params.username, firstName: params.firstName, lastName: params.lastName,email: params.email)
                usr.errors.rejectValue("username","user.username.exists")
                render view: "register", model: [user: usr]
                return
            }

            User usr = new User(username:  params.username ,password:  params.password,
                    firstName:  params.firstName, lastName:  params.lastName,
                    email: params.email)
            def userRole = Role.findOrSaveWhere(authority: "ROLE_USER")

            if (params.password != params.confirm)
            {
                usr.errors.rejectValue("password", "user.password.doesntmatch")
                render view:"register", model: [user: usr]
                return
            }

            if(userValidator.isValid(usr))
            {
                if(userValidator.isUsernameValid(usr))
                {
                    if(userValidator.isPasswordValid(usr))
                    {
                        userInitializer.assignRole(usr,userRole,true)
                        springSecurityService.reauthenticate(usr.username,usr.password)
                        redirect controller: 'main',action:'confirm'
                        return
                    }
                }
            }
            render view:"register", model: [user: usr]
        }
    }

}
