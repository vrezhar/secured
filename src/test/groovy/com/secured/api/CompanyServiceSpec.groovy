package com.secured.api

import com.secured.api.resources.CompanyBuildingSource
import com.secured.auth.User
import com.secured.data.BarCode
import com.secured.data.Company
import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

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
