package com.ttreport

import com.ttreport.data.Document
import grails.gorm.transactions.Transactional
import groovy.json.JsonSlurper

@Transactional
class Base64EncoderService {

    def convertToEncodableString(Document document)
    {
        StringBuilder result = new StringBuilder('{')
        final String key_closer = '":"'
        final String value_closer = '",'
        result.append(']}')
    }

    String encode(String input) {
        return input.encodeAsBase64().toString()
    }
}
