package com.ttreport.main

import com.ttreport.admin.EnableCommand
import com.ttreport.auth.Role
import com.ttreport.auth.User
import com.ttreport.auth.UserInitializerService
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

    @Secured(['ROLE_ADMIN'])
    def enable(EnableCommand cmd)
    {
        println(cmd)
        User user = User.findWhere(username: cmd.username)
        userInitializerService.enable(user)
        user.save(true)
        render(view:"/user/list",model: [users: User.list()])
    }

    @Secured(['ROLE_ADMIN'])
    def disable(EnableCommand cmd)
    {
        println(cmd)
        User user = User.findWhere(username: cmd.username)
        if(user.authorities.contains(Role.findWhere(authority: "ROLE_ADMIN"))){
            render(view:"/user/list",model: [users: User.list()])
            return
        }
        userInitializerService.disable(user)
        user.save(true)
        render(view:"/user/list",model: [users: User.list()])
    }

    @Secured(['permitAll'])
    def home()
    {
        if(!springSecurityService.isLoggedIn()){
            render view: "default"
            return
        }
        redirect controller: "user", action: "profile"
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
            redirect controller: 'user', action: 'profile'
            return
        }
        render view: '/error'
    }

    @Secured(["permitAll"])
    def respond()
    {

    }

}
