package com.secured.auth

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN','ROLE_USER'])
class MainController {
    static defaultAction = 'home'
    SpringSecurityService springSecurityService
    LinkEncoderService linkEncoderService

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

    @Secured(["ROLE_USER","ROLE_ADMIN"])
    def confirm()
    {
        render view: "confirm", model: [user: springSecurityService.getCurrentUser()]
    }

    @Secured(["ROLE_USER","ROLE_ADMIN"])
    def verify()
    {
        def token = User.findWhere(mainToken: linkEncoderService.decode(params.token))
        if(token)
            redirect controller:'main', action: 'home'
        render view: '/error'
    }


}
