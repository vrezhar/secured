package com.secured.main

import com.secured.auth.User
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured


class UserController  {

    SpringSecurityService springSecurityService

    @Secured(['ROLE_USER','ROLE_ADMIN'])
    def show(long id)
    {
        User user = User.findById(id)
        if(user)
        {
            render model: [user: user], view: "details"
            return
        }
        render model: [user: springSecurityService.getCurrentUser()], view: "details"
    }

}
