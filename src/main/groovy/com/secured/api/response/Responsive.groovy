package com.secured.api.response

import grails.config.Config
import grails.core.support.GrailsConfigurationAware

class Responsive implements GrailsConfigurationAware
{

    protected static Map statusCodes = [:]

    @Override
    void setConfiguration(Config co)
    {
        statusCodes.success = co.status.OK
        statusCodes.invalid_token = co.status.INVALID_TOKEN
        statusCodes.invalid_input = co.status.INVALID_ACTION
    }
}
