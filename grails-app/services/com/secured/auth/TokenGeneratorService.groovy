package com.secured.auth

import grails.gorm.transactions.Transactional

@Transactional
class TokenGeneratorService {

    String generate(User usr)
    {
        int predicate = 0;
        for(int i = 0; i < usr.username.size(); ++i)
        {
            char c = usr.username.charAt(i)
            predicate += c
        }
        return Math.abs((predicate | (1<<31))).toString()
    }
}
