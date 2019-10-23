package com.ttreport.api.current

import grails.gorm.transactions.Transactional

@Transactional
class ValidationErrorResolverService {

    protected String getMessage(String message) {
        def props = new Properties()
        new File("grails-app/i18n/messages.properties").withInputStream {
            stream -> props.load(stream)
        }
        return props[message]
    }

    protected int getCode(String message)
    {
        String error = getMessage(message)
        if(error.length() != 3){
            return -1;
        }
        int result = 0
        for(int i = 0; i < 3; ++i){
            result = result*10 + error[i].toInteger()
        }
        return result
    }

}
