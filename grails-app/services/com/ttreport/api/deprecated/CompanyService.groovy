package com.ttreport.api.deprecated

import com.ttreport.api.resources.deprecated.CompanyBuildingSource
import com.ttreport.configuration.ApplicationConfiguration
import com.ttreport.auth.User
import com.ttreport.data.Company
import com.ttreport.logs.ServerLogger
import grails.gorm.transactions.Transactional

@Transactional
class CompanyService extends ApplicationConfiguration
{

    def save(CompanyBuildingSource src)
    {
        ServerLogger.log("save() called")
        Map response = [:]
        if(!src.validate())
        {
            ServerLogger.log("command object not validated, exiting save()")
            response.status = apiStatusCodes.invalid_input
            return response
        }
        if(src.mainToken == null && src.companyToken == null)
        {
            ServerLogger.log("no token registered, exiting save()")
            response.status = apiStatusCodes.invalid_token
            return response
        }
        User user = User.findWhere(mainToken: src.mainToken)
        if(!user)
        {
            ServerLogger.log("invalid user token, exiting save()")
            response.status = apiStatusCodes.invalid_token
            return response
        }
        ServerLogger.log("trying to save a company")
        Company company = new Company(address: src.address,
                                      inn: src.inn,
                                      user: user)
        if(company.validate())
        {
            user.addToCompanies(company)
            company.save()
            user.save()
            response.company_token = company.token
            response.status = apiStatusCodes.success
            ServerLogger.log("company saved, reporting success, exiting save()")
            return response
        }
        ServerLogger.log("company not saved, reporting failure, exiting save()")
        response.status = apiStatusCodes.invalid_input
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
            response.status = apiStatusCodes.invalid_token
            return response
        }
        company.token = regenerateToken()
        company.save()
        response.new_company_token = company.token
        response.status = apiStatusCodes.success
        return response
    }

}
