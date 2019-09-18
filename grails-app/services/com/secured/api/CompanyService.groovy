package com.secured.api

import com.secured.api.resources.CompanyBuildingSource
import com.secured.auth.User
import com.secured.data.Company
import grails.gorm.transactions.Transactional

@Transactional
class CompanyService {

    def registerCompany(CompanyBuildingSource src)
    {
        if(src == null ||
                src.address == "" ||
                src.companyId == "" ||
                src.address == null ||
                src.companyId == null)
            return "INVALID_INPUT"
        if(src.mainToken == null || src.mainToken == "")
            return "INVALID_TOKEN"
        User user = User.findWhere(mainToken: src.mainToken)
        if(!user)
            return "INVALID_TOKEN"
        Company company = new Company(address: src.address,
                                      companyId: src.companyId,
                                      user: user)
        if(company.validate())
        {
            user.addToCompanies(company)
            company.save()
            user.save()
            return company.token
        }
        return "INVALID_INPUT"
    }

    protected static String regenerateToken()
    {
       return UUID.randomUUID().toString()
    }

    def update(CompanyBuildingSource src)
    {

        Company company = Company.findByToken(src.companyToken)
        if(!company)
           return "INVALID_TOKEN"
        company.token = regenerateToken()
        company.save()
        return company.token
    }
}
