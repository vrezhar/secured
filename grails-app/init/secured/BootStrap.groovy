
package secured


import com.secured.auth.Role
import com.secured.auth.User
import com.secured.auth.UserRole
import com.secured.data.Company
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
