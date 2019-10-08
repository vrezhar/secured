package com.secured.user

import com.secured.auth.User
import com.secured.data.Company

import com.secured.signature.SignatureVerificationService
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured


class UserController  {

    SpringSecurityService springSecurityService
    SignatureVerificationService signatureVerificationService

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

    @Secured(['ROLE_USER','ROLE_ADMIN'])
    def createCompany()
    {
        SignatureCommand signature = flash?.errorCommand ?: new SignatureCommand()
        render view: "sign", model: [signature: signature]
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

    @Secured(['permitAll'])
    def confirm(SignatureCommand cmd)
    {
        if(cmd == null)
        {
            println("am null")
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
            println("saving")
            company.save()
            withFormat{
                this.response.status = 200
                json{
                    respond(status: 200)
                }
            }
        }
        println("huh")
        withFormat{
            this.response.status = 404
            json{
                respond(status: 404)
            }
        }
    }
}
