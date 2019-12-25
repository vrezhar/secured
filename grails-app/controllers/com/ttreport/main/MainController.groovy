package com.ttreport.main

import com.ttreport.admin.EnableCommand
import com.ttreport.auth.EmailCommand
import com.ttreport.auth.Role
import com.ttreport.auth.User
import com.ttreport.auth.UserInitializerService
import com.ttreport.mail.Mail
import com.ttreport.mail.strategy.handlers.error.RejectEmail
import com.ttreport.mail.strategy.senders.SendViaPostMarkAPI
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured

import java.security.cert.X509Certificate


@Secured(['ROLE_ADMIN','ROLE_USER'])
class MainController
{

    static defaultAction = 'home'
    static scope = 'session'

    SpringSecurityService springSecurityService
    UserInitializerService userInitializerService

    private EmailCommand errorCommand = null

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
        //println((X509Certificate[])request.getAttribute("javax.servlet.request.X509Certificate"))
        render view: "default"
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
    def registrationError()
    {
        render view: 'registrationError'
    }

    @Secured(["permitAll"])
    def verify()
    {
        User usr = User.findWhere(mainToken: params.token)
        if(usr)
        {
            userInitializerService.enable(usr)
            userInitializerService.updateToken()
            springSecurityService.reauthenticate(usr.username,usr.password)
            redirect controller: 'user', action: 'profile'
            return
        }
        render view: '/notFound'
    }

    @Secured(["permitAll"])
    def aboutUs()
    {
        render view: 'aboutus'
    }

    @Secured(["permitAll"])
    def contactUs()
    {
        render view: 'contactus'
    }

    @Secured(['permitAll'])
    def forgotPasswordEmail()
    {
        render view: '/passwordRecovery/forgotPasswordEmail', model: [command: errorCommand?: new EmailCommand()]
    }

    @Secured(['permitAll'])
    def forgotPassword(EmailCommand cmd)
    {
        println(cmd.email)
        println(params)
        println(request)
        if(!cmd || !cmd?.validate()){
            println('command not validated')
            cmd?.errors?.rejectValue('email','Entered email is invalid')
            errorCommand = cmd
            render view: 'registrationError'
            return
        }
        String text = "<html><p>Please Follow this link to reset your password</p><p><a href = 'www.ttreport.ru/recover/confirm?token=${User.findByUsername(cmd.email).mainToken}'>www.ttreport.ru/recover/confirm?token=${User.findByUsername(cmd.email).mainToken}</a></p></html>"
        new Mail()
            .from('support@ttreport.ru')
            .to(cmd.email)
            .withSubject('Reset password')
            .useSendingStrategy(SendViaPostMarkAPI.usingDefaultToken())
            .onSuccess { ->
                render view: '/passwordRecovery/forgotPassword'
            }
            .onErrors { Mail mail, Exception e ->
                RejectEmail.withMessage(e.message).handleErrors(mail,e)
                redirect controller: 'main', action: 'registrationError'
            }
            .withMessage(text)
            .send()
    }

}
