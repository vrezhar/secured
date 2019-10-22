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
        Company company = Company.findWhere(token: cmd.companyToken)

        if(!company) {
            dandr.document = null
            dandr.response = Response.rejectToken(cmd.companyToken)
            return dandr
        }
        if(!cmd.validate()) {
            dandr.document = null
            dandr.response = Response.rejectInput(cmd,"command object not validated, exiting acceptProducts()")
            return dandr
        }

        Response response = new Response()
        Document document = createAcceptanceDocumentMock(cmd)
        for(item in cmd.products) {
            if (item.product_code) {
                DevCycleLogger.log("found product with code ${item.product_code}, belonging to company with id ${company.companyId}")
                Products products = update(item)
                boolean contains = document.products?.contains(products)
                if (!products || !products?.validate()) {
                    DevCycleLogger.log("unable to update product with code ${item.product_code}, belonging to company with id ${company.companyId}, rejecting")
                    response.rejectProduct(item)
                    response.reportInvalidInput()
                    continue
                }
                products.save(true)
                if (!contains) {
                    document.addToProducts(products) // necessary,belongsTo in products is not defined
                }
                response.accept(products)
                DevCycleLogger.log("adding product with code ${item.product_code}, belonging to company with id ${company.companyId}, to the current document")
                continue
            }

            DevCycleLogger.log("product with code ${item.product_code}, belonging to company with id ${company.companyId} not found, trying to save")
            Products products = save(item, company)
            if (!products || !products?.validate()) {
                DevCycleLogger.log_validation_errors(products)
                DevCycleLogger.log("unable to save product with code ${item.product_code}, belonging to company with id ${company.companyId}, rejecting")
                response.rejectProduct(item)
                response.reportInvalidInput()
                continue
            }

            DevCycleLogger.log("product with code ${item.product_code}, belonging to company with id ${company.companyId} saved, adding to current document")
            products.save(true)
            document.addToProducts(products)
            response.accept(products)
        }

        dandr.document = document
        dandr.response = response.getAsMap()

        return dandr
    }

    protected DocumentAndResponse shipProducts(ShipmentDocumentCommand cmd)
    {
        DocumentAndResponse dandr = new DocumentAndResponse()
        if(!cmd.validate()) {
            dandr.document = null
            dandr.response = Response.rejectInput(cmd,"command object not validated, exiting acceptProducts()")
            return dandr
        }
        Company company = Company.findWhere(token: cmd.companyToken)
        if(!company) {
            dandr.document = null
            dandr.response = Response.rejectToken(cmd.companyToken)
            return dandr
        }

        Response response = new Response()
        Document document = createShipmentDocumentMock(cmd)

        for(item in cmd.products) {
            Products products = Products.get(item.product_code)
            if(products && company?.has(products)) {
                boolean contains = document.products?.contains(products)
                DevCycleLogger.log("found product with code ${item.product_code}, belonging to company with id ${company.companyId}, trying to delete")
                delete(item,products)
                if(!products) {
                    DevCycleLogger.log("couldn't delete barcode with uit code ${item.uit_code} and uitu code ${item.uitu_code} with code ${item.product_code}, belonging to company with id ${company.companyId}, rejecting")
                    response.rejectProduct(item)
                    response.reportInvalidInput()
                    continue
                }
                if(!contains) {
                    document.addToProducts(products)
                }
                response.accept(products)
                continue
            }
            DevCycleLogger.log("couldn't find product with code ${item.product_code}, belonging to company with id ${company.companyId}, rejecting")
            response.rejectProduct(item)
            response.reportInvalidInput()
        }
        DevCycleLogger.log("exiting shipProducts()")
        dandr.document = document
        dandr.response = response.getAsMap()

        return dandr
    }
}
