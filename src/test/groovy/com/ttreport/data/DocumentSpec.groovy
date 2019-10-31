package com.ttreport.data

import com.ttreport.auth.Role
import com.ttreport.auth.User
import com.ttreport.auth.UserRole
import com.ttreport.logs.DevCycleLogger
import grails.test.hibernate.HibernateSpec

class DocumentSpec extends HibernateSpec{

    List<Class> getDomainClasses()
    {
        [BarCode, Products, Company, User, UserRole, User, Role]
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
        BarCode barCode = new BarCode(uit_code: "test", uitu_code: "test1", products: products)
        BarCode barCode1 = new BarCode(uit_code: "ahhh", uitu_code: "duh", products: products)
        barCode1.save()
        barCode.save()
        Document document = new Document(requestType: "Acceptance")
        document.addToBarCodes(barCode)
        document.addToBarCodes(barCode1)
        String result_base64 = Document.encodeAsBase64(document)
        String result_json = Document.serializeAsJson(document)
        println(new String(result_base64.decodeBase64()))
        DevCycleLogger.print_logs()
        expect:"test encoding and decoding"
            new String(result_base64.decodeBase64()) == result_json
    }
}
