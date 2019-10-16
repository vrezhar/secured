
package ttreport


import com.ttreport.auth.Role
import com.ttreport.auth.User
import com.ttreport.auth.UserRole
import com.ttreport.data.Company
import grails.compiler.GrailsCompileStatic



@GrailsCompileStatic
class BootStrap {


    def init = { servletContext ->
        Role adminRole = Role.findOrSaveWhere(authority: 'ROLE_ADMIN')
        User admin = User.findWhere(username: 'testmail@gmail.com')
        if(!admin)
        {
            admin = new User(username: 'testmail@gmail.com', password: '1Test',
                             firstName: 'Admin', lastName: 'Admin',)
            admin.mainToken = "admins_credentials"
            admin.enabled = true
            admin.save()
            UserRole.create(admin, adminRole)
        }
        admin.save()
        Company company = Company.findWhere(address: "Komitas", companyId: "Initial", user: admin)
        if(!company){
            company = new Company(address: "Komitas", companyId: "Initial", user: admin)
            admin.addToCompanies(company)
            company.save(true)
        }
        println(company.token)
    }

    def destroy = {
    }
}
