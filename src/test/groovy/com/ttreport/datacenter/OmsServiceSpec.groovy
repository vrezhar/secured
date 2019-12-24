package com.ttreport.datacenter

import com.ttreport.auth.Role
import com.ttreport.auth.User
import com.ttreport.auth.UserRole
import com.ttreport.data.Company
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.data.documents.differentiated.remains.RemainsDescriptionDocument
import com.ttreport.data.documents.differentiated.remains.RemainsDocument
import com.ttreport.data.documents.differentiated.remains.RemainsFullDescriptionDocument
import com.ttreport.data.documents.differentiated.remains.RemainsRegistryDocument
import com.ttreport.data.products.BarCode
import com.ttreport.data.products.Products
import com.ttreport.data.products.remains.FullRemainsProduct
import com.ttreport.data.products.remains.Orders
import com.ttreport.data.products.remains.ProductOrderUnit
import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest

class OmsServiceSpec extends HibernateSpec implements ServiceUnitTest<OmsService>{

    def setup() {
    }

    def cleanup() {
    }

    List<Class> getDomainClasses()
    {
        [Document, FullRemainsProduct, RemainsDocument, RemainsDescriptionDocument, RemainsRegistryDocument, RemainsFullDescriptionDocument, Company, User, Products, BarCode, Orders, ProductOrderUnit, UserRole, Role]
    }

    void "test something"() {
        Role testRole = new Role(authority: "ROLE_TEST")
        testRole.save()
        User user = new User(firstName: "user", lastName: "userson", username: "user@userson.com", password: "1Test")
        user.save()
        UserRole.create(user, testRole)
        Company company = new Company(address: "Komitas", companyId: "1", user: user, omsId: "0794f376-7135-486d-a748-8d91fec417227", omsToken: "17c8c49-dab2-bc02-4e82-a58b46cabf66")
        company.save()
        Orders order = new Orders()
        expect:"fix me"
            true == false
    }
}
