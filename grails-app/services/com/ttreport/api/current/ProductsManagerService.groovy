package com.ttreport.api.current

import com.ttreport.api.resources.current.AcceptanceDocumentCommand
import com.ttreport.api.resources.current.DocumentAndResponse
import com.ttreport.api.resources.current.ShipmentDocumentCommand
import com.ttreport.api.response.current.Response
import com.ttreport.data.Company
import com.ttreport.data.Document
import com.ttreport.data.Products
import com.ttreport.logs.DevCycleLogger
import grails.gorm.transactions.Transactional

@Transactional
class ProductsManagerService extends DocumentService{

    protected DocumentAndResponse acceptProducts(AcceptanceDocumentCommand cmd)
    {
        DevCycleLogger.log("acceptProducts() called")
        DocumentAndResponse dandr = new DocumentAndResponse()
        Response response = performCommandValidation(cmd)
        if(response.status == 400 || response.status == 402){
            dandr.response = response.getAsMap()
            return  dandr
        }
        Company company = Company.findWhere(token: cmd.companyToken)
        Document document = createAcceptanceDocumentMock(cmd)
        for(item in cmd.products) {
            if(!item.rejected)
            {
                if (item.action == "UPDATE") {
                    DevCycleLogger.log("found product with code ${item.product_code}, belonging to company with id ${company.companyId}")
                    Products products
                    try{
                        products = update(item, 0/*,company*/)
                    }
                    catch(Exception e){
                        DevCycleLogger.log(e.message)
                        dandr.response.status = 505
                        return dandr
                    }
                    boolean contains = document.products?.contains(products)
                    products.save(true)
                    if (!contains) {
                        document.addToProducts(products) // necessary,belongsTo in products is not defined
                    }
                    response.accept(item)
                    DevCycleLogger.log("adding product with code ${item.product_code}, belonging to company with id ${company.companyId}, to the current document")
                    continue
                }

                DevCycleLogger.log("product with code ${item.product_code}, belonging to company with id ${company.companyId} not found, trying to save")
                Products products
                try{
                    products = save(item, company)
                }
                catch (Exception e){
                    DevCycleLogger.log(e.message)
                    dandr.response = response.getAsMap()
                    dandr.response.status = 505
                    return dandr
                }
                DevCycleLogger.log("product with code ${item.product_code}, belonging to company with id ${company.companyId} saved, adding to current document")
                boolean contains = document.products?.contains(products)
                products.save(true)
                if (!contains) {
                    document.addToProducts(products) // necessary,belongsTo in products is not defined
                }
                response.accept(item)
            }
        }
        if(!document.products || document.products?.size() == 0)
        {
            dandr.response = response.getAsMap()
            return dandr
        }
        dandr.document = document
        dandr.response = response.getAsMap()

        return dandr
    }

    protected DocumentAndResponse shipProducts(ShipmentDocumentCommand cmd)
    {
        DocumentAndResponse dandr = new DocumentAndResponse()
        Response response = performCommandValidation(cmd)
        if(response.status == 400 || response.status == 402){
            dandr.response = response.getAsMap()
            return  dandr
        }
        Company company = Company.findWhere(token: cmd.companyToken)
        Document document = createShipmentDocumentMock(cmd)

        for(item in cmd.products) {
            if(!item.rejected){
                Products products
                try{
                    products = delete(item)
                }
                catch (Exception e){
                    DevCycleLogger.log(e.message)
                    dandr.response.status = 505
                    return dandr
                }
                boolean contains = document.products?.contains(products)
                DevCycleLogger.log("found product with code ${item.product_code}, belonging to company with id ${company.companyId}, trying to delete")
                if(!contains) {
                    document.addToProducts(products)
                }
                response.accept(item)
            }
        }
        if(!document.products || document.products?.size() == 0)
        {
            dandr.response = response.getAsMap()
            return dandr
        }
        DevCycleLogger.log("exiting shipProducts()")
        dandr.document = document
        dandr.response = response.getAsMap()

        return dandr
    }
}
