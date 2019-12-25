package com.ttreport.configuration

import grails.config.Config
import grails.core.support.GrailsConfigurationAware


class ApplicationConfiguration implements GrailsConfigurationAware
{

    static Map apiStatusCodes = [:]
    static Map mailConfigs = [:]

    @Override
    void setConfiguration(Config co)
    {
        apiStatusCodes.success = co.status.OK
        apiStatusCodes.invalid_token = co.status.INVALID_TOKEN
        apiStatusCodes.invalid_input = co.status.INVALID_ACTION
        mailConfigs.postmark_token = co.mail.token
        mailConfigs.sender = co.mail.email
        mailConfigs.gmail_account = co.mail.email
        mailConfigs.password = co.mail.password
    }
}
