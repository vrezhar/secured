package com.secured.api.resources

import grails.validation.Validateable

class TokenRegeneratingSource implements Validateable
{

    String companyToken
    static constraints = {
    }
}
