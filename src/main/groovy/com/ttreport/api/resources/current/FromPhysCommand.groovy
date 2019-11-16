package com.ttreport.api.resources.current

import com.ttreport.data.Company
import com.ttreport.data.Products
import grails.validation.Validateable

class FromPhysCommand implements Validateable
{
    String companyToken
    List<ProductCommand> products_list

    static constraints = {
        companyToken nullable: false, validator: { String value, FromPhysCommand object ->
            Company company = Company.findWhere(token: value)
            if(!company){
                return false
            }
        }
        products_list nullable: false

    }
}
