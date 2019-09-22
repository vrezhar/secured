package com.secured.logs

import com.secured.auth.User
import com.secured.data.BarCode
import com.secured.data.Company
import com.secured.data.Products
import com.secured.data.connectors.CompanyProducts
import com.secured.data.connectors.ProductsBarcodes

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
        (CompanyProducts.findAllWhere(company: company) as List<Products>)*.each{
            println("product code: ${it.productCode}, description: ${it.description}")
        }
    }
    static void list_all_barcodes()
    {
        BarCode.list().each{
            println("code: ${it.code}, belongs to: ${it.company.companyId} at ${it.company.address}")
        }
    }
    static void list_barcodes_of(Company company)
    {
        println("printing barcodes of ${company.user}'s company with id ${company.companyId}")
        company.barcodes.each{
            println("code: ${it.code}")
        }
    }
    static void list_barcodes_of(Company company,Products products)
    {
        println("printing barcodes assigned to ${products.productCode} of ${company.user}'s company with id ${company.companyId}")
        (company.getBarCodesOf(products))*.each{
            println("code: ${it.code}")
        }
    }
    static void list_barcodes_of(Products products)
    {
        println("printing barcodes assigned to ${products.productCode}")
        products.allBarCodes.each{
            println("code: ${it.code}")
        }
    }
    static void log(String action)
    {
        action_logs.add(action)
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
