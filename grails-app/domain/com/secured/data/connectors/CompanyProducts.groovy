package com.secured.data.connectors

import com.secured.data.Company
import com.secured.data.Products
import grails.compiler.GrailsCompileStatic
import groovy.transform.ToString


@GrailsCompileStatic
@ToString(cache=true, includeNames=true, includePackage=false)
class CompanyProducts implements Serializable
{
    private static final long serialVersionUID = 1

    Company company
    Products products

    static CompanyProducts create(Company company, Products products, boolean flush = false)
    {
        CompanyProducts companyProducts = new CompanyProducts(products: products, company: company)
        companyProducts.save(flush)
        return companyProducts
    }

    static constraints = {
    }
}
