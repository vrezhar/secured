package com.secured.api

import com.secured.api.resources.BarCodeRegisteringSource
import com.secured.auth.User
import com.secured.data.BarCode
import com.secured.data.Company
import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class BarCodeServiceSpec extends HibernateSpec implements ServiceUnitTest<BarCodeService>{

    List<Class> getDomainClasses()
    {
        [BarCode, User, Company, BarCodeRegisteringSource]
    }


    void "test barcode registration"()
    {
        given:
        User usr = new User(username: "test", password: "passworD1",email: "bruh@Moment.com",
                firstName: "bruh", lastName: "moment")
        Company company = new Company(address: "komitas",companyId: "1", user: usr)
        BarCodeRegisteringSource src = new BarCodeRegisteringSource(barcodes: [])
        src.companyToken = company.token
        when:
        usr.save()
        company.save(true)
        then:
        //Company.findWhere(token: barCodeRegisteringSource.companyToken) != null
        Company.list() != []

        when:
        src.barcodes.add("test")
        service.registerBarCodes(src)
        then:
        Company.findByAddress( "komitas").barCodes[0].code == "test"

    }
}
