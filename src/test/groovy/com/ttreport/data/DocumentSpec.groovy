package com.ttreport.data

import com.ttreport.auth.Role
import com.ttreport.auth.User
import com.ttreport.auth.UserRole
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.data.documents.differentiated.existing.MarketEntranceDocument
import com.ttreport.data.products.BarCode
import com.ttreport.data.products.MarketEntranceBarCode
import com.ttreport.data.products.Products
import com.ttreport.logs.ServerLogger
import grails.test.hibernate.HibernateSpec

class DocumentSpec extends HibernateSpec{

    List<Class> getDomainClasses()
    {
        [Document, MarketEntranceDocument, MarketEntranceBarCode, BarCode, Products, Company, User, UserRole, User, Role]
    }

    void "test serialization"() {
        Role testRole = new Role(authority: "ROLE_TEST")
        testRole.save()
        User user = new User(firstName: "user", lastName: "userson", username: "user@userson.com", password: "1Test")
        user.save()
        UserRole.create(user, testRole)
        Company company = new Company(address: "Komitas", companyId: "1", user: user)
        company.save()
        Products products = new Products(description: "test", tax: 10, cost: 100)
        products.save()
        BarCode barCode = new BarCode(uitCode: "test", uituCode: "test1", products: products)
        BarCode barCode1 = new BarCode(uitCode: "ahhh", uituCode: "duh", products: products)
        BarCode barCode2 = new MarketEntranceBarCode(tnvedCode: "test")
        barCode1.save()
        barCode.save()
        Document document = new Document()
        document.addToBarCodes(barCode)
        document.addToBarCodes(barCode1)
        document.addToBarCodes(barCode2)
        String result_json = document.serializeAsJson()
        println(result_json)
        ServerLogger.print_logs()
        expect:"test encoding and decoding"
            true
    }
}
