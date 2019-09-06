package com.secured.auth

import grails.gorm.transactions.Transactional

@Transactional
class LinkEncoderService {

    def encode(String token)
    {
        return token
    }
    def decode(String token)
    {
        return token
    }
    private static String generateHash(String string)
    {
        return  null
    }
}
