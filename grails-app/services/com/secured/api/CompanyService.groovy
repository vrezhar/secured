package com.secured.api

import com.secured.api.resources.CompanyBuildingSource
import com.secured.api.response.Responsive
import com.secured.auth.User
import com.secured.data.Company

import grails.gorm.transactions.Transactional

@Transactional
class CompanyService extends Responsive
{

    def save(CompanyBuildingSource src)
    {
        Map response = [:]
        if(!src.validate())
        {
            response.status = statusCodes.invalid_input
            return response
        }
        if(src.mainToken == null && src.companyToken == null)
        {
            response.status = statusCodes.invalid_token
            return response
        }
        User user = User.findWhere(mainToken: src.mainToken)
        if(!user)
        {
            response.status = statusCodes.invalid_token
            return response
        }
        Company company = new Company(address: src.address,
                                      companyId: src.companyId,
                                      user: user)
        if(company.validate())
        {
            user.addToCompanies(company)
            company.save()
            user.save()
            response.company_token = company.token
            response.status = statusCodes.success
            return response
        }
        response.status = statusCodes.invalid_input
        return response
    }

    protected static String regenerateToken()
    {
       return UUID.randomUUID().toString()
    }

    def update(CompanyBuildingSource src)
    {
        Map response = [:]
        Company company = Company.findByToken(src.companyToken)
        if(!company)
        {
            response.status = statusCodes.invalid_token
            return response
        }
        company.token = regenerateToken()
        company.save()
        response.new_company_token = company.token
        response.status = statusCodes.success
        return response
    }

}
