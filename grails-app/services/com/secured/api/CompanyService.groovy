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
            return "COMPANY_NOT_CREATED"
        User user = User.findWhere(mainToken: src.mainToken)
        if(!user)
            return "COMPANY_NOT_CREATED"
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
        return "COMPANY_NOT_CREATED"
    }
    def regenerateToken()
    {
       return UUID.randomUUID().toString()
    }
}
