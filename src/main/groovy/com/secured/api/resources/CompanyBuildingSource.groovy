package com.secured.api.resources

import grails.validation.Validateable

class CompanyBuildingSource implements Validateable
{

    String mainToken
    String companyId
    String address
    static constraints = {
    }
}
