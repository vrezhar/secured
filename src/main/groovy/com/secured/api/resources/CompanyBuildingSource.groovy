package com.secured.api.resources

import grails.validation.Validateable

class CompanyBuildingSource implements Validateable
{

    String mainToken
    String companyToken = ""
    String companyId
    String address
    static constraints = {
    }
}
