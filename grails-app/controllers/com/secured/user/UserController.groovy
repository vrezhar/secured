package com.secured.user

import com.secured.auth.Role
import com.secured.auth.User
import com.secured.data.Company
import com.secured.logs.DevCycleLogger


import com.secured.signature.SignatureVerificationService
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured


class UserController  {

    SpringSecurityService springSecurityService
    SignatureVerificationService signatureVerificationService

    static defaultAction = "profile()"

    @Secured(['ROLE_USER','ROLE_ADMIN'])
    def show(long id)
    {
        User user = User.findById(id)
        if(user && (springSecurityService.currentUser as User).authorities?.contains(Role.findWhere(authority: 'ROLE_ADMIN')))
        {
            render model: [user: user], view: "profile"
            return
        }
        render model: [user: springSecurityService.getCurrentUser()], view: "profile"
    }

    @Secured(['ROLE_ADMIN'])
    def list()
    {

    }

    @Secured(['ROLE_USER','ROLE_ADMIN'])
    def showCompanies()
    {
        render model: [user: springSecurityService.getCurrentUser()], view: "companies"
    }

    @Secured(['ROLE_USER','ROLE_ADMIN'])
    def profile()
    {
        render view: "profile",model: [user: springSecurityService.getCurrentUser()]
    }


    @Secured(['ROLE_USER','ROLE_ADMIN'])
    def createCompany()
    {
        render view: "sign", model: [user: springSecurityService.currentUser as User]
    }

    @Secured(['ROLE_USER','ROLE_ADMIN'])
    def sign(SignatureCommand cmd)
    {
        if(!cmd.validate())
        {
            withFormat {
                json{
                    respond([companyId: null, address: null])
                }
            }
        }
        Company company = signatureVerificationService.verify(cmd.body)
        if(company)
        {

            if(springSecurityService.getCurrentUser() == company.user)
            {
                withFormat {
                    json{
                        respond(company)
                    }
                }
            }
        }
        withFormat {
            json{
                respond([companyId: null, address: null])
            }
        }
    }

    @Secured(['ROLE_ADMIN','ROLE_USER'])
    def confirm(SignatureCommand cmd)
    {
        DevCycleLogger.log("accept called ")
        if(cmd == null)
        {
            DevCycleLogger.log("command object is null, exiting confirm()")
            withFormat{
                this.response.status = 400
                json{
                    respond(status: 400)
                }
            }
        }
        Company company = signatureVerificationService.verify(cmd.body)
        if(company)
        {
            DevCycleLogger.log("saving company")
            company.save()
            withFormat{
                this.response.status = 200
                json{
                    respond(status: 200)
                }
            }
        }
        DevCycleLogger.log("Critical error occurred while creating company(reasons unknown)")
        withFormat{
            this.response.status = 404
            json{
                respond(status: 404)
            }
        }
    }
}
