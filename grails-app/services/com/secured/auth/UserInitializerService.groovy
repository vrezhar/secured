package com.secured.auth

import grails.gorm.transactions.Transactional

@Transactional
class UserInitializerService  implements  UserInitializer{

    @Override
    def assignRole(User usr, String r,boolean flush = true)
    {
        usr.save()
        def role = Role.findOrSaveWhere(authority: r)
        if(!usr.authorities.contains(role))
            UserRole.create(usr,role,flush)

    }


    @Override
    def assignRole(User usr, Role role,boolean flush = true)
    {
        usr.save()
        if(!Role.find(role))
            role.save()
        if(!usr.authorities.contains(role))
            UserRole.create(usr,role,flush)

    }

}
