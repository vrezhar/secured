package com.ttreport

import com.ttreport.data.Document
import grails.gorm.transactions.Transactional

@Transactional
class Base64EncoderService {

    String serializeAsJson(Document document)
    {
       return Document.serializeAsJson(document)
    }

    String encodeAsBase64(Document document)
    {
        return serializeAsJson(document).bytes.encodeAsBase64()
    }

    String decodeBase64(String input){
        return new String(input.decodeBase64())
    }
}
