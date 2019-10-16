package com.ttreport.logs

import com.ttreport.auth.User
import com.ttreport.data.BarCode
import com.ttreport.data.Company
import com.ttreport.data.Products
import grails.artefact.DomainClass
import grails.validation.Validateable

class DevCycleLogger
{
    static ArrayList<String> action_logs = []
    static void list_all_companies()
    {
        println("printing companies:")
        Company.list().each{
            println("address: ${it.address}, id: ${it.companyId}")
        }
    }
    static void list_companies_of(User user)
    {
        println("printing companies of ${user.username}:")
        user.companies.each{
            println("address: ${it.address}, id: ${it.companyId}")
        }
    }
    static void list_all_products()
    {
        println("printing products:")
        Products.list().each{
            println("product code: ${it.productCode}, description: ${it.description}")
        }
    }
    static void list_products_of(Company company)
    {
        println("printing products of ${company.user}'s company with id ${company.companyId}")
        company.products.each{
            println("product code: ${it.productCode}, description: ${it.description}")
        }
    }
    static void list_all_barcodes()
    {
        BarCode.list().each{
            println("uit_code: ${it.uit_code}, uitu_code: ${it.uitu_code}, belongs to: ${it.products.productCode} ")
        }
    }
    static void list_barcodes_of(Company company)
    {
        println("printing barcodes of ${company.user}'s company with id ${company.companyId}")
        company.products.each {
            it.barCodes.each {
                println("uit_code: ${it.uit_code}, uitu_code: ${it.uitu_code}")
            }
        }
    }
    static void list_barcodes_of(Products products)
    {
        println("printing barcodes assigned to ${products.productCode}")
        products.barCodes.each{
            println("uit_code: ${it.uit_code}, uitu_code: ${it.uitu_code}")
        }
    }
    static void log(String action)
    {
        action_logs.add(action)
    }
    static void log_validation_errors(Validateable cmd, String additional_message = null)
    {
        cmd.errors.fieldErrors.each {
            log("value ${it.rejectedValue} not validated for field ${it.field}")
        }
        if(additional_message)
        {
            log(additional_message)
        }
    }
    static void log_validation_errors(DomainClass cmd, String additional_message = null)
    {
        cmd.errors.fieldErrors.each {
            log("value ${it.rejectedValue} not validated for field ${it.field}")
        }
        if(additional_message)
        {
            log(additional_message)
        }
    }
    static void print_logs()
    {
        println("showing all registered logs:")
        action_logs.each {
            println(it)
        }
    }
    static void cleanup()
    {
        println("dumped previous ${action_logs.size()} records")
        action_logs.clear()
    }
}
