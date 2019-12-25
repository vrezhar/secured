package com.ttreport.mail

import grails.config.Config
import grails.core.support.GrailsConfigurationAware

class MailingConfiguration implements GrailsConfigurationAware{

    static Map mailConfigs = [:]

    @Override
    void setConfiguration(Config co)
    {
        println(co)
        mailConfigs.postmark_token = co.mail.token
        mailConfigs.sender = co.mail.email
        mailConfigs.gmail_account = co.mail.email
        mailConfigs.password = co.mail.password
    }
}
