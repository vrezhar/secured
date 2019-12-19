package com.ttreport.api

import com.ttreport.data.Company

trait DocumentCommandObject
{
    abstract String getCompanyToken()
    abstract void setCompanyToken(String companyToken)

    Company authorize()
    {
        return Company.findWhere(token: getCompanyToken())
    }
}