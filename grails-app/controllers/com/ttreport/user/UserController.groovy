package com.ttreport.user

import com.ttreport.auth.Role
import com.ttreport.auth.User
import com.ttreport.data.Company
import com.ttreport.logs.ServerLogger


import com.ttreport.signature.SignatureVerificationService
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
            render model: [user: user], view: "profile_old"
            return
        }
        render model: [user: springSecurityService.getCurrentUser()], view: "profile_old"
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
        render view: "profile"
    }


    @Secured(['ROLE_USER','ROLE_ADMIN'])
    def createCompany()
    {
        render view: "sign_old", model: [user: springSecurityService.currentUser as User]
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
        ServerLogger.log("accept called ")
        if(cmd == null)
        {
            ServerLogger.log("command object is null, exiting confirm()")
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
            ServerLogger.log("saving company")
            company.save()
            withFormat{
                this.response.status = 200
                json{
                    respond(status: 200)
                }
            }
        }
        ServerLogger.log("Critical error occurred while creating company(reasons unknown)")
        withFormat{
            this.response.status = 404
            json{
                respond(status: 404)
            }
        }
    }
}
