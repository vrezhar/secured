package com.ttreport.auth

import grails.gorm.transactions.Transactional

@Transactional
class UserInitializerService  implements  UserInitializer{

    @Override
    def assignRole(User usr, String r,boolean flush = true)
    {
        if(usr == null || r == null)
            return false
        usr.save()
        def role = Role.findOrSaveWhere(authority: r)
        if(!usr.authorities.contains(role))
        {
            UserRole.create(usr,role,flush)
            return true
        }
        return false
    }


    @Override
    def assignRole(User usr, Role role,boolean flush = true)
    {
        if(usr == null || role == null)
            return false
        usr.save()
        if(!Role.find(role))
            role.save()
        if(!usr.authorities.contains(role)) {
            UserRole.create(usr, role, flush)
            return true
        }
        return false

    }

    def enable(User usr)
    {
        if(usr == null)
            return false
        usr.enabled = true
        usr.save()
        return true
    }

    def disable(User usr)
    {
        if(usr == null)
            return false
        usr.enabled = false
        usr.save()
        return true
    }

    def lockAccount(User usr)
    {
        if(usr == null)
            return false
        usr.accountLocked = true
        usr.save()
        return true
    }

    def unlockAccount(User usr)
    {
        if(usr == null)
            return false
        usr.accountLocked = false
        usr.save()
        return true
    }

}
