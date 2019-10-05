package com.secured.auth

import com.secured.data.Company
import com.secured.signature.Signature
import com.secured.user.UserCommand
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class User implements Serializable {

    private static final long serialVersionUID = 1

    String username
    String password
    String firstName
    String lastName
    String email
    String mainToken = UUID.randomUUID().toString()
    Date dateCreated
    Date lastUpdated

    static hasMany = [companies: Company]
    static hasOne = [signature: Signature]

    boolean enabled = false
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    Set<Role> getAuthorities()
    {
        (UserRole.findAllByUser(this) as List<UserRole>)*.role as Set<Role>
    }

    static User createUser(UserCommand cmd)
    {
        return new User(username: cmd.username, password: cmd.password,
                        firstName: cmd.firstName, lastName: cmd.lastName,
                        email: cmd.email)
    }

    static constraints = {
        password nullable: false, blank: false, password: true
        username nullable: false, blank: false, unique: true
        firstName nullable: false, blank: false
        lastName nullable: false, blank: false
        email nullable: false,blank: false,email: true,unique: true
        mainToken unique: true
    }

    static mapping = {

    }

}



