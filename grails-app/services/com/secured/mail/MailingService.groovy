package com.secured.mail

import com.secured.Mail
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
