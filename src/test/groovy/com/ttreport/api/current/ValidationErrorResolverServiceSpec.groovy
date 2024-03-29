package com.ttreport.api.current

import com.ttreport.api.resources.current.products.ProductCommand
import com.ttreport.auth.Role
import com.ttreport.auth.User
import com.ttreport.auth.UserRole
import com.ttreport.data.products.BarCode
import com.ttreport.data.Company
import com.ttreport.data.products.Products
import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest

class ValidationErrorResolverServiceSpec extends HibernateSpec implements ServiceUnitTest<ValidationErrorResolverService>{

    List<Class> getDomainClasses()
    {
        [BarCode, Products, Company, User, Role, UserRole]
    }

    void "test something"() {
        ProductCommand error = new ProductCommand()
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
        BarCode barCode = new BarCode(uitCode: "test", uituCode: "test1", products: products)
        products.addToBarCodes(barCode)
        barCode.save()
        error.setAction("SAVE")
        error.id = 1
        error.uit_code = "011111111111111121asasasasddddd"
        error.uitu_code = "test1"
        error.validate()
        error.errors.fieldErrors.each {
            println(it.field+": "+it.code)
        }
        expect:"matching error"
               true

    }
}
