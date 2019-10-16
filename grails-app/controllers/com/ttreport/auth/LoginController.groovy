package com.ttreport.auth

class LoginController extends grails.plugin.springsecurity.LoginController
{
    def auth()
    {
        if (springSecurityService.isLoggedIn()) {
            redirect uri: "/user/profile"
            return
        }
        String postUrl = request.contextPath + conf.apf.filterProcessesUrl
        render view: 'auth', model: [postUrl: postUrl,
                                     rememberMeParameter: conf.rememberMe.parameter,
                                     usernameParameter: conf.apf.usernameParameter,
                                     passwordParameter: conf.apf.passwordParameter,
                                     gspLayout: conf.gsp.layoutAuth,]
    }
}
