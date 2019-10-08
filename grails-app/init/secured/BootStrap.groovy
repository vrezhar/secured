
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
        User admin = User.findWhere(username: 'admin')
        if(!admin)
        {
            admin = new User(username: 'admin', password: '1Test',
                             firstName: 'Admin', lastName: 'Admin',
                             email: "testmail@gmail.com")
            admin.mainToken = "admins_credentials"
            admin.enabled = true
            admin.save()
            UserRole.create(admin, adminRole)
        }
        admin.save()
        Company company = Company.findOrSaveWhere(address: "Komitas", companyId: "Initial", user: admin)
        admin.addToCompanies(company)
        company.save(true)
        println(company.token)
    }

    def destroy = {
    }
}
