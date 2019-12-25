package com.ttreport.auth

import grails.gorm.transactions.Transactional

@Transactional
class UserInitializerService  implements  UserInitializer{

    static scope = 'prototype'

    @Override
    def assignRoleAndSave(User usr, String r, boolean flush = true)
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
    def assignRoleAndSave(User usr, Role role, boolean flush = true)
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

    def updatePassword(User usr, String newPassword)
    {
        if(!usr || !newPassword){
            return false
        }
        usr.password = newPassword
        if(usr.save()){
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

    def updateToken(User usr)
    {
        if(usr == null)
            return false
        usr.mainToken = UUID.randomUUID().toString()
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
