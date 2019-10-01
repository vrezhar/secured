package com.secured.api.deprecated

import com.secured.api.resources.deprecated.CompanyBuildingSource
import com.secured.api.response.Responsive
import com.secured.auth.User
import com.secured.data.Company
import com.secured.logs.DevCycleLogger
import grails.gorm.transactions.Transactional

@Transactional
class CompanyService extends Responsive
{

    def save(CompanyBuildingSource src)
    {
        DevCycleLogger.log("save() called")
        Map response = [:]
        if(!src.validate())
        {
            DevCycleLogger.log("command object not validated, exiting save()")
            response.status = statusCodes.invalid_input
            return response
        }
        if(src.mainToken == null && src.companyToken == null)
        {
            DevCycleLogger.log("no token registered, exiting save()")
            response.status = statusCodes.invalid_token
            return response
        }
        User user = User.findWhere(mainToken: src.mainToken)
        if(!user)
        {
            DevCycleLogger.log("invalid user token, exiting save()")
            response.status = statusCodes.invalid_token
            return response
        }
        DevCycleLogger.log("trying to save a company")
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
            DevCycleLogger.log("company saved, reporting success, exiting save()")
            return response
        }
        DevCycleLogger.log("company not saved, reporting failure, exiting save()")
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
