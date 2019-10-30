package com.ttreport.mail

import grails.config.Config
import grails.core.support.GrailsConfigurationAware

class MailingConfigurationAware implements GrailsConfigurationAware{
    protected static Map<String,String> mailConfigs = [:]

    @Override
    void setConfiguration(Config co)
    {
        mailConfigs.postmark_token = co.mail.postmark.api.token
        mailConfigs.default_sender = co.mail.sender.email
        mailConfigs.gmail_account = co.mail.gmail.email
        mailConfigs.gmail_password = co.mail.gmail.password
    }
}
