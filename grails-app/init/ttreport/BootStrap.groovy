
package ttreport


import com.ttreport.auth.Role
import com.ttreport.auth.User
import com.ttreport.auth.UserRole
import com.ttreport.data.Company
import com.ttreport.admin.testKeystoreLoading
import grails.compiler.GrailsCompileStatic



@GrailsCompileStatic
class BootStrap {


    def init = { servletContext ->
        Role adminRole = Role.findOrSaveWhere(authority: 'ROLE_ADMIN')
        Role userRole = Role.findOrSaveWhere(authority: 'ROLE_USER')
        User admin = User.findWhere(username: 'testmail@gmail.com')
        User user = User.findWhere(username: "test@test.com")
        if(!admin)
        {
            admin = new User(username: 'testmail@gmail.com', password: '1Test',
                             firstName: 'Admin', lastName: 'Admin',)
            admin.mainToken = "admins_credentials"
            admin.enabled = true
            admin.save()
            UserRole.create(admin, adminRole)
        }
        if(!user){
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
        if(!test){
            test = new Company(address: "Komitas", companyId: "Initial",token: "test", user: user)
            user.addToCompanies(test)
            test.save()
        }
        if(!company){
            company = new Company(address: "Komitas", companyId: "Initial", user: admin)
            admin.addToCompanies(company)
            company.save(true)
        }
        println(company.token)
//        testKeystoreLoading.test()
    }

    def destroy = {
    }
}
