package com.secured.user

import com.secured.auth.User
import grails.gorm.transactions.Transactional

@Transactional
class UserService {

    void activateUser(User user) {
        user.enabled = true
        user.save(true)
    }
}
