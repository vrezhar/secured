package com.secured.main

import com.secured.auth.User
import com.secured.user.UserService
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_USER'])
class MainController {
    static defaultAction = 'home'

    SpringSecurityService springSecurityService
    UserService userService

    @Secured(['ROLE_ADMIN'])
    def list()
    {
        render(view:"/user/userList",model: [users: User.list()])
    }


    @Secured(['ROLE_ADMIN','ROLE_USER'])
    def home()
    {
        render view:"home", model: [user: springSecurityService.getCurrentUser().username ]
    }

    @Secured(["permitAll"])
    def confirm()
    {
        render view: "confirm", model: [user: springSecurityService.getCurrentUser()]
    }

    @Secured(["permitAll"])
    def verify()
    {
        User usr = User.findWhere(mainToken: params.token)
        if(usr)
        {
            userService.activateUser(usr)
            springSecurityService.reauthenticate(usr.username,usr.password)
            redirect controller: 'main', action: 'home'
        }
        render view: '/error'
    }


}
