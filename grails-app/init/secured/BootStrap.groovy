
package secured


import com.secured.auth.Role
import com.secured.auth.User
import com.secured.auth.UserRole
import com.secured.signature.Signature
import grails.compiler.GrailsCompileStatic



@GrailsCompileStatic
class BootStrap {


    def init = { servletContext ->
        def adminRole = Role.findOrSaveWhere(authority: 'ROLE_ADMIN')
        def signature = Signature.findOrSaveWhere(body: "admins_signature")
        def admin = User.findWhere(username: 'admin')
        if(!admin)
        {
            admin = new User(username: 'admin', password: '1Test',
                             firstName: 'Admin', lastName: 'Admin',
                             email: "testmail@gmail.com")
            admin.mainToken = "admins_credentials"
            admin.enabled = true
            admin.save()
            UserRole.create(admin, adminRole, true)
        }
        admin.signature = admin.signature ?: signature
        admin.save()
    }

    def destroy = {
    }
}
