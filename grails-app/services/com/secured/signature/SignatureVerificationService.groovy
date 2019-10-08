package com.secured.signature

import com.secured.auth.User
import com.secured.data.Company
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import groovy.json.JsonSlurper

@Transactional
class SignatureVerificationService
{

    SpringSecurityService springSecurityService

    private static Map<String,String> matcher =
            [
                    "admins_signature" : '{"company_id":"15","company_address":"Komitas"}',
                    "test_signature" : '{"company_id":"25","company_address":"Charents"}',
                    "signature" : '{"company_id":"35","company_address":"New York"}'
            ]

    Company verify(String signature) {
        User user = springSecurityService.currentUser as User
        for(entry in matcher)
        {
            if(entry.key == signature)
            {
                JsonSlurper slurper = new JsonSlurper()
                def fields = slurper.parseText(entry.value)
                return new Company(address: fields.company_address, companyId: fields.company_id, user: user)
            }
        }
        return null
    }

    Company save(String signature)
    {
        Company company = verify(signature)
        company.save()
        return company
    }

}
