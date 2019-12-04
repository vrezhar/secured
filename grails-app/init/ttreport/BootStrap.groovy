
package ttreport

import com.ttreport.api.response.current.Response
import com.ttreport.api.types.DocumentType
import com.ttreport.auth.Role
import com.ttreport.auth.User
import com.ttreport.auth.UserRole
import com.ttreport.configuration.ApplicationConfiguration
import com.ttreport.data.Company
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.data.documents.differentiated.existing.*
import com.ttreport.datacenter.DataCenterApiConnectorService
import com.ttreport.logs.ServerLogger
import com.ttreport.mail.MailingConfiguration
import grails.async.Promise
import grails.compiler.GrailsCompileStatic

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

import static grails.async.Promises.task

@GrailsCompileStatic
class BootStrap {

    DataCenterApiConnectorService dataCenterApiConnectorService

    def init = {
        servletContext ->
            Role adminRole = Role.findOrSaveWhere(authority: 'ROLE_ADMIN')
            Role userRole = Role.findOrSaveWhere(authority: 'ROLE_USER')
            User admin = User.findWhere(username: 'testmail@gmail.com')
            User user = User.findWhere(username: "test@test.com")
            if (!admin) {
                admin = new User(username: 'testmail@gmail.com', password: '1Test',
                        firstName: 'Admin', lastName: 'Admin',)
                admin.mainToken = "admins_credentials"
                admin.enabled = true
                admin.save()
                UserRole.create(admin, adminRole)
            }
            if (!user) {
                user = new User(username: 'test@test.com', password: '2Test',
                        firstName: 'User', lastName: 'User',)
                user.mainToken = "test_user"
                user.enabled = true
                user.save()
                UserRole.create(user, userRole)
            }
            user.save()
            admin.save()
            Company company = Company.findWhere(address: "Komitas", companyId: "Initial", user: admin)
            Company test = Company.findWhere(address: "Komitas", companyId: "test", user: user)
            if (!test) {
                test = new Company(address: "Komitas", companyId: "Initial", token: "test", user: user)
                user.addToCompanies(test)
                test.save()
            }
            if (!company) {
                company = new Company(address: "Komitas", companyId: "Initial", user: admin)
                admin.addToCompanies(company)
                company.save(true)
            }

//            DataCenterApiConnectorService.updateToken()

            Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {

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

                @Override
                void run() {
                    ServerLogger.log("Document sender executor started, iterating over all documents")
                    final Object monitor = new Object()
                    int threadCount = 1
                    for(document in Document.list()){
                        if (!document.documentId) {
                            ServerLogger.log("found unsent document, sending")
                            Promise p = task({
                                ++threadCount
                                dataCenterApiConnectorService.sendDocument(document,inferType(document),true)
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
                }
            }, 2, 2, TimeUnit.HOURS)
    }

    def destroy = {
    }
}
