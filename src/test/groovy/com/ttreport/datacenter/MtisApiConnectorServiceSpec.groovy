package com.ttreport.datacenter

import com.ttreport.api.types.DocumentType
import com.ttreport.auth.Role
import com.ttreport.auth.User
import com.ttreport.auth.UserRole
import com.ttreport.data.products.BarCode
import com.ttreport.data.Company
import com.ttreport.data.products.MarketEntranceBarCode
import com.ttreport.data.products.Products
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.data.documents.differentiated.GenericDocument
import com.ttreport.data.documents.differentiated.existing.AcceptanceDocument
import com.ttreport.data.documents.differentiated.existing.ConsumerReleaseDocument
import com.ttreport.data.documents.differentiated.existing.MarketEntranceDocument
import com.ttreport.data.documents.differentiated.existing.RFIEntranceDocument
import com.ttreport.data.documents.differentiated.existing.ShipmentDocument
import com.ttreport.logs.ServerLogger
import grails.async.Promise
import grails.test.hibernate.HibernateSpec
import grails.testing.services.ServiceUnitTest
import groovy.json.JsonBuilder

import static grails.async.Promises.task

class MtisApiConnectorServiceSpec extends HibernateSpec implements  ServiceUnitTest<MtisApiConnectorService>{

    List<Class> getDomainClasses()
    {
        [Document, GenericDocument, AcceptanceDocument, MarketEntranceDocument, Company, User, Products, BarCode, UserRole, Role]
    }


    def setup(){
        assert service != null
    }

    void "test acceptance"()
    {
        Products products = new Products(description: "Тапочки мужские, Арт. 1010-2019/1 размер 41, синие", cost: 1, tax: 0)
        BarCode barCode = new BarCode(uitCode: "010460084036212021'nKjg(Y&QMvp%", products: products)
        BarCode anotherBarCode = new BarCode(uitCode: "010460084036203821+XT3MX_NLegQT", products: products)
        Document document = new AcceptanceDocument(releaseOrderNumber: "ba5922e2-5c32-4147-a8f3-9eb70d45fe33",
                documentNumber: 1,
                tradeSenderInn: "503208655100",
                tradeSenderName: "ИП Ватажицын Сергей Андреевич",
                transferDate: "21.11.2019",
                turnoverType: "SALE",
                tradeRecipientInn: "5053032781",
                documentDate: new Date().toInstant().toString(),
                acceptanceDate: new Date().toInstant().toString())
        document.addToBarCodes(barCode)
        document.addToBarCodes(anotherBarCode)
        service.sendDocument(document, (DocumentType.ACCEPTANCE), true)
        println(document.documentId)
        println(document.documentStatus)
        expect:
        document.documentId
    }

    void "test release document"()
    {
        Document document = new ConsumerReleaseDocument()
        Products products = new Products(description: "Тапочки мужские, Арт. 1010-2019/1 размер 41, синие", cost: 1, tax: 0)
        BarCode barCode = new BarCode(uitCode: "010460084036212021'nKjg(Y&QMvp%", products: products)
        BarCode anotherBarCode = new BarCode(uitCode: "010460084036203821+XT3MX_NLegQT", products: products)
        document.addToBarCodes(barCode)
        document.addToBarCodes(anotherBarCode)
        service.sendDocument(document, (DocumentType.RELEASE), true)
        println(document.documentId)
        println(document.documentStatus)
        expect:
        document.documentId
    }

    void "test api connection"()
    {
        service.updateToken()
        expect:
        service.getInfo(new Document(documentId: "ba5922e2-5c32-4147-a8f3-9eb70d45fe33"), true)
    }

    DocumentType inferType(Document document) {
        if (document instanceof AcceptanceDocument) {
            return DocumentType.ACCEPTANCE
        }
        if (document instanceof ShipmentDocument) {
            return DocumentType.SHIPMENT
        }
        if (document instanceof ConsumerReleaseDocument) {
            return DocumentType.RELEASE
        }
        if (document instanceof MarketEntranceDocument) {
            return DocumentType.ENTRANCE
        }
        if (document instanceof RFIEntranceDocument) {
            return DocumentType.INDIVIDUAL
        }
        return null
    }

    void "test executor"(){
        User user = new User(username: 'test@test.com', password: '2Test',
                firstName: 'User', lastName: 'User',)
        user.mainToken = "test_user"
        user.enabled = true
        user.save()
        Role userRole = new Role(authority: "ROLE_TEST")
        userRole.save()
        UserRole.create(user, userRole)
        Company test = new Company(address: "Komitas", companyId: "Initial", token: "test", user: user)
        user.addToCompanies(test)
        test.save()

        for (int i = 0; i < 10; i++) {
            Document document = new AcceptanceDocument(releaseOrderNumber: "ba5922e2-5c32-4147-a8f3-9eb70d45fe33",
                    documentNumber: 1,
                    tradeSenderInn: "503208655100",
                    tradeSenderName: "ИП Ватажицын Сергей Андреевич",
                    transferDate: "21.11.2019",
                    turnoverType: "SALE",
                    tradeOwnerInn: "503208655100",
                    tradeRecipientInn: "5053032781",
                    documentDate: new Date().toInstant().toString(),
                    acceptanceDate: new Date().toInstant().toString(),
                    company: test)
            Products products = new Products(description: "Тапочки мужские, Арт. 1010-2019/1 размер 41, синие", cost: 1, tax: 0, company: test)
            BarCode barCode = new BarCode(uitCode: "010460084036212021'nKjg(Y&QMvp%", products: products)
            BarCode anotherBarCode = new BarCode(uitCode: "010460084036203821+XT3MX_NLegQT", products: products)
            document.addToBarCodes(barCode)
            document.addToBarCodes(anotherBarCode)
            if(!document.save()){
                ServerLogger.log_validation_errors(document,"",true)
                throw new Exception("document not created")
            }
        }

        final Object monitor = new Object()
        int threadCount = 1
        for(document in Document.list()){
            if (!document.documentId) {
                ServerLogger.log("found unsent document, sending")
                Promise p = task({
                    ++threadCount
                    service.sendDocument(document,inferType(document),true)
                })
                p.onError { Throwable t ->
                    synchronized (monitor){
                        --threadCount
                        ServerLogger.log_exception(t)
                        monitor.notifyAll()
                    }
                }
                p.onComplete { Object ignored ->
                    synchronized(monitor){
                        ServerLogger.log("document sent")
                        --threadCount
                        monitor.notifyAll()
                    }
                }
            }
            while (threadCount > 4){
                synchronized (monitor){
                    monitor.wait()
                }
            }
        }
        expect:
        Document.list()[3].documentId
    }

    void "test production"()
    {

        JsonBuilder builder = null
        byte[] signedCode = null
        try {
            BarCode barCode = new MarketEntranceBarCode("uitCode": "0104600840362120212Xg3Cz3KFIjDI",
                    "uituCode": "",
                    "tnvedCode": "6401100000",
                    "certificateDocument": "CONFORMITY_DECLAR",
                    "certificateDocumentNumber": "FFFFFF",
                    "certificateDocumentDate": new Date().toInstant().toString())
            Company company = new Company(inn: "5053032781")
            Document document = new MarketEntranceDocument("participantInn": "5053032781",
                    "productionDate": new Date().toInstant().toString(),
                    "producerInn": "5053032781",
                    "ownerInn": "5053032781",
                    "productionType": "OWN_PRODUCTION",
                    "docType": "Promotion_Inform_Selfmade",
                    company: company)
            document.addToBarCodes(barCode)
            service.sendDocument(document, DocumentType.ENTRANCE)
            println(document)
        }
        catch (Exception e)
        {
            println("exception occurred, error message:")
            println(e.message)
            println("stack trace:")
            e.stackTrace.each {
                println(it.toString())
            }
        }
        println(builder?.toString())

        expect:
        !null
    }
}
