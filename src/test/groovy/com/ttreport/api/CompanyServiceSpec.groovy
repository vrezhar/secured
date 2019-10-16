package com.ttreport.api

import com.ttreport.api.deprecated.CompanyService
import com.ttreport.api.resources.deprecated.CompanyBuildingSource
import com.ttreport.auth.User
import com.ttreport.data.BarCode
import com.ttreport.data.Company
import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest

class CompanyServiceSpec extends HibernateSpec implements ServiceUnitTest<CompanyService>
{

    List<Class> getDomainClasses()
    {
        [BarCode, User, Company, CompanyBuildingSource]
    }

    void "test company creation"()
    {
        when:
        User usr = new User(username: "test", password: "passworD1",email: "bruh@Moment.com",
        firstName: "bruh", lastName: "moment")
        usr.save()

        then:
        User.findWhere(username: "test") != null
        Company.list() == []

        when:
        CompanyBuildingSource src = new CompanyBuildingSource()
        src.mainToken = usr.mainToken;
        src.address = "komitas"
        src.companyId = "Numba one"
        service.registerCompany(src)
        then:
        Company.findByCompanyId(src.companyId).address == "komitas"
    }
}
