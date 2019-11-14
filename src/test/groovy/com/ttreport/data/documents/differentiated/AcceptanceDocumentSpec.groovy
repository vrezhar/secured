package com.ttreport.data.documents.differentiated

import com.ttreport.auth.Role
import com.ttreport.auth.User
import com.ttreport.auth.UserRole
import com.ttreport.data.BarCode
import com.ttreport.data.Company
import com.ttreport.data.Products
import com.ttreport.data.documents.differentiated.existing.AcceptanceDocument
import com.ttreport.logs.DevCycleLogger
import grails.test.hibernate.HibernateSpec

class AcceptanceDocumentSpec extends HibernateSpec {

    List<Class> getDomainClasses()
    {
        [Company, User, UserRole, Document, BarCode, Products, AcceptanceDocument]
    }

    void "test something"() {
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
        Document document = new AcceptanceDocument(requestType: "ACCEPTANCE",tradeOwnerInn: "test",
                                                   tradeOwnerName: "test", tradeRecipientInn: "test",
                                                   tradeSenderInn: "test",tradeSenderName: "test",transferDate: 1,
                                                   turnoverType: "SALE",acceptanceDate: 1,releaseOrderNumber: 5,
                                                   documentNumber: "15", company: company)
        document.addToBarCodes(barCode)
        document.addToBarCodes(barCode1)
        document.save()
        DevCycleLogger.log_validation_errors(document)
        DevCycleLogger.print_logs()
        expect:"fix me"
            AcceptanceDocument.list()
    }
}