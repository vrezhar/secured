package com.secured.main

import com.secured.auth.User
import com.secured.auth.UserInitializerService
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_USER'])
class MainController {
    static defaultAction = 'home'

    SpringSecurityService springSecurityService
    UserInitializerService userInitializerService

    @Secured(['ROLE_ADMIN'])
    def list()
    {
        render(view:"/user/list",model: [users: User.list()])
    }


    @Secured(['ROLE_ADMIN','ROLE_USER'])
    def home()
    {
        render view:"home", model: [user: springSecurityService.getCurrentUser().username ]
    }

    @Secured(["permitAll"])
    def confirm()
    {
        if(flash?.message != "pending"){
            render view: "/notFound"
            return false
        }
        render view: "confirm"
    }

    @Secured(["permitAll"])
    def verify()
    {
        User usr = User.findWhere(mainToken: params.token)
        if(usr)
        {
            userInitializerService.enable(usr)
            springSecurityService.reauthenticate(usr.username,usr.password)
            redirect controller: 'main', action: 'home'
            return
        }
        render view: '/error'
    }


}
