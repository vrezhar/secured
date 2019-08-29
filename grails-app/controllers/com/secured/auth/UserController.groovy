package com.secured.auth


import grails.plugin.springsecurity.annotation.Secured

@Secured(['permitAll'])
class UserController  {

    PatternValidatorService patternValidator
    SecurityCoordinateGeneratorService securityCoordinateGenerator
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
                return [user: usr]
            }
            usr = new User(username:  params.username ,password:  params.password, firstName:  params.firstName, lastName:  params.lastName)
            if(!usr.validate())
                return [user: usr]
            if (params.password != params.confirm)
            {
                usr.errors.rejectValue("password", "user.password.doesntmatch")
                return [user: usr]
            }

            if(!patternValidator.validateUsername(usr.username))
            {
                usr.errors.rejectValue("username","user.username.incorrect")
                return [user: usr]
            }
            switch(patternValidator.validatePassword(usr.password))
            {
                case 0:
                    usr.errors.rejectValue("password","user.password.tooshort")
                    return [user: usr]
                case -1:
                    usr.errors.rejectValue("password","user.password.toolong")
                    return [user: usr]
                case 1:
                    usr.errors.rejectValue("password","user.password.tooweak")
                    return [user: usr]
            }
            Map<String,String> securityCard = securityCoordinateGenerator.generateCoordinates()

            userInitializer.addToCoordinates(usr,securityCard,true)
            userInitializer.assignRole(usr,userRole,true)


            springSecurityService.reauthenticate(usr.username,usr.password)
            flash.securitycard = securityCard
            redirect controller: 'main',action:'confirm'

        }
    }

}
