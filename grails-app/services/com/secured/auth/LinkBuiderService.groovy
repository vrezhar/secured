package com.secured.auth

import grails.gorm.transactions.Transactional

@Transactional
class LinkBuiderService
{
    final static String host = "localhost:8080"
    def build(String token, boolean enforsehttps = false)
    {
        String protocol = enforsehttps ? "https://www." : "http://www."
        String link = protocol + host + "/verify?token=" + token
        return link
    }
}
