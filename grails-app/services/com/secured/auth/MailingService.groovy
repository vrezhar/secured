package com.secured.auth

import grails.gorm.transactions.Transactional

@Transactional
class MailingService {

    private Mail mail
    def compose()
    {
        mail = new Mail()
        return mail
    }

}
