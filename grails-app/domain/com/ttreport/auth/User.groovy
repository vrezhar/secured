package com.ttreport.auth

import com.ttreport.data.Company

import com.ttreport.user.UserCommand
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
    String mainToken = UUID.randomUUID().toString()

    Date dateCreated
    Date lastUpdated

    static hasMany = [companies: Company]

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
                        firstName: cmd.firstName, lastName: cmd.lastName)
    }

    public int getBarCodes()
    {
        int barcodesCount = 0;
        this.companies.each {
            it.products.each {
                barcodesCount += it.barCodes.size()
            }
        }
        return barcodesCount
    }

    static constraints = {
        password nullable: false, blank: false, password: true
        username nullable: false, blank: false, email: true, unique: true
        firstName nullable: false, blank: false
        lastName nullable: false, blank: false
        mainToken unique: true
    }

    static mapping = {

    }

}



