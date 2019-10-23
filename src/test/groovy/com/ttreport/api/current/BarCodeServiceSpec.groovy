package com.ttreport.api.current

import com.ttreport.auth.Role
import com.ttreport.auth.User
import com.ttreport.auth.UserRole
import com.ttreport.data.BarCode
import com.ttreport.data.Company
import com.ttreport.data.Products
import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class BarCodeServiceSpec extends HibernateSpec implements ServiceUnitTest<BarCodeService>{


    List<Class> getDomainClasses()
    {
        [BarCode, Products, Company, User, Role, UserRole]
    }

    void "test not deleted uit code detection"() {
        when:
        Role testRole = new Role(authority: "ROLE_TEST")
        testRole.save()
        User user = new User(firstName: "user", lastName: "userson", username: "user@userson.com", password: "1Test")
        user.save()
        UserRole.create(user, testRole)
        Company company = new Company(address: "Komitas", companyId: "1", user: user)
        user.addToCompanies(company)
        company.save()
        Products products = new Products(description: "test", tax: 10, cost: 100)
        company.addToProducts(products)
        products.save()
        BarCode barCode = new BarCode(uit_code: "test1774", uitu_code: "testable", products: products)
//        barCode.dateDeleted = new Date()
        products.addToBarCodes(barCode)
        barCode.save()
        String testValue = service.findNotDeletedByUitCode("test1774")?.uitu_code
        then:
        testValue == "testable"
    }
}