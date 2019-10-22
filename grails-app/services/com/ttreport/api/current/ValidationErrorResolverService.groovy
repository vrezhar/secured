package com.ttreport.api.current

import grails.gorm.transactions.Transactional

@Transactional
class ValidationErrorResolverService {

    def getMessage(String message) {
        def props = new Properties()
        new File("grails-app/i18n/messages.properties").withInputStream {
            stream -> props.load(stream)
        }
        return props[message]
    }
}
