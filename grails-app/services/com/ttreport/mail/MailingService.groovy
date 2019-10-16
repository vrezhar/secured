package com.ttreport.mail

import com.ttreport.Mail
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
