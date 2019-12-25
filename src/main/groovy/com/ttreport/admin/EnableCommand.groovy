package com.ttreport.admin

import grails.validation.Validateable

class EnableCommand implements Validateable
{
    String username
    static constraints ={
        username blank: false, nullable: false
    }
}
