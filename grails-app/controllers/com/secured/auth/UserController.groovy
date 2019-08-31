package com.secured.auth


import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class UserController  {

    PatternValidatorService patternValidator
    UserInitializerService userInitializer
    def springSecurityService

    def register()
    {

        if(request.method == 'POST')
        {
            def userRole = Role.findOrSaveWhere(authority: "ROLE_USER")
            User usr = User.findWhere(username: params.username)
            if(usr)
            {
                usr = new User(username: params.username, firstName: params.firstName, lastName: params.lastName)
                usr.errors.rejectValue("username","user.username.exists")
                render view: "register", model: [user: usr]
                return
            }
            usr = new User(username:  params.username ,password:  params.password, firstName:  params.firstName, lastName:  params.lastName)
            if(!usr.validate()) {
                render view: "register", model: [user: usr]
                return
            }
            if (params.password != params.confirm)
            {
                usr.errors.rejectValue("password", "user.password.doesntmatch")
                render view:"register", model: [user: usr]
                return
            }

            if(!patternValidator.validateUsername(usr.username))
            {
                usr.errors.rejectValue("username","user.username.incorrect")
                render view:"register", model: [user: usr]
            }
            switch(patternValidator.validatePassword(usr.password))
            {
                case 0:
                    usr.errors.rejectValue("password","user.password.tooshort")
                    render view:"register", model: [user: usr]
                    return
                case -1:
                    usr.errors.rejectValue("password","user.password.toolong")
                    render view:"register", model: [user: usr]
                    return
                case 1:
                    usr.errors.rejectValue("password","user.password.tooweak")
                    render view:"register", model: [user: usr]
                    return
            }
            userInitializer.assignRole(usr,userRole,true)
            springSecurityService.reauthenticate(usr.username,usr.password)
            flash.securitycard = "I'll fix this up later"
            redirect controller: 'main',action:'confirm'

        }
    }

}
