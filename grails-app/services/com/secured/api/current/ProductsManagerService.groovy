package com.secured.api.current

import com.secured.api.resources.current.AcceptanceDocumentCommand
import com.secured.api.resources.current.DocumentAndResponse
import com.secured.api.resources.current.ShipmentDocumentCommand
import com.secured.api.response.current.Response
import com.secured.data.Company
import com.secured.data.Document
import com.secured.data.Products
import com.secured.logs.DevCycleLogger
import grails.gorm.transactions.Transactional
import grails.validation.Validateable
import org.apache.commons.lang.Validate

@Transactional
class ProductsManagerService extends DocumentService{

    protected DocumentAndResponse acceptProducts(AcceptanceDocumentCommand cmd)
    {
        DevCycleLogger.log("acceptProducts() called")

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
        Document document = createAcceptanceDocumentMock(cmd)
        for(it in cmd.products) {
            Products products = Products.findWhere(productCode: it.product_code)
            if (products) {
                DevCycleLogger.log("found product with code ${it.product_code}, belonging to company with id ${company.companyId}")
                boolean contains = document.products?.contains(products)
                products = update(it)
                if (!products || !products?.validate()) {
                    DevCycleLogger.log("unable to update product with code ${it.product_code}, belonging to company with id ${company.companyId}, rejecting")
                    response.rejectProduct(it)
                    response.reportInvalidInput()
                    continue
                }
                products.save()
                if (!contains) {
                    document.addToProducts(products) // necessary,belongsTo in products is not defined
                }
                DevCycleLogger.log("adding product with code ${it.product_code}, belonging to company with id ${company.companyId}, to the current document")
                continue
            }

            DevCycleLogger.log("product with code ${it.product_code}, belonging to company with id ${company.companyId} not found, trying to save")
            products = save(it, company)
            if (!products || products?.validate()) {
                DevCycleLogger.log_validation_errors(products)
                DevCycleLogger.log("unable to save product with code ${it.product_code}, belonging to company with id ${company.companyId}, rejecting")
                response.rejectProduct(it)
                response.reportInvalidInput()
                continue
            }

            DevCycleLogger.log("product with code ${it.product_code}, belonging to company with id ${company.companyId} saved, adding to current document")
            products.save()
            document.addToProducts(products)
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

        for(it in cmd.products) {
            Products products = Products.findWhere(productCode:  it.product_code)
            if(products && company?.products?.contains(products)) {
                boolean contains = document.products?.contains(products)
                DevCycleLogger.log("found product with code ${it.product_code}, belonging to company with id ${company.companyId}, trying to delete")
                products = delete(it)
                if(!products) {
                    DevCycleLogger.log("couldn't delete product with code ${it.product_code}, belonging to company with id ${company.companyId}, rejecting")
                    response.rejectProduct(it)
                    response.reportInvalidInput()
                    continue
                }
                if(!contains) {
                    document.addToProducts(products)
                }
                continue
            }
            DevCycleLogger.log("couldn't find product with code ${it.product_code}, belonging to company with id ${company.companyId}, rejecting")
            response.rejectProduct(it)
            response.reportInvalidInput()
        }
        DevCycleLogger.log("exiting shipProducts()")
        dandr.document = document
        dandr.response = response.getAsMap()

        return dandr
    }
}
