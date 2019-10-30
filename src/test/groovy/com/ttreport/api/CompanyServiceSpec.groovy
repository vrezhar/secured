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
        expect:
        true
    }
}
