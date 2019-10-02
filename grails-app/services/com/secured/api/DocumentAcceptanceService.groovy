package com.secured.api

import com.secured.api.resources.AcceptanceDocumentCommand
import com.secured.api.resources.ProductCommand
import com.secured.api.response.Response
import com.secured.data.Company
import com.secured.data.Document
import com.secured.data.Products
import com.secured.logs.DevCycleLogger
import grails.gorm.transactions.Transactional

@Transactional
class DocumentAcceptanceService extends  DocumentService
{

    protected Map acceptProducts(Document document, Company company, List<ProductCommand> productsList)
    {
        DevCycleLogger.log("acceptProducts() called")
        Response response = new Response()
upper:  for(it in productsList)
        {
            Products products = Products.findWhere(productCode: it.product_code)
            if(products)
            {
                DevCycleLogger.log("found product with code ${it.product_code}, belonging to company with id ${company.companyId}")
                products = update(it)
                if(!products)
                {
                    DevCycleLogger.log("unable to update product with code ${it.product_code}, belonging to company with id ${company.companyId}, rejecting")
                    response.rejectProduct(it)
                    response.reportInvalidInput()
                    continue
                }
                for(product in document.products)
                {
                    if(products.productCode == product.productCode)
                    {
                        product = products
                        DevCycleLogger.log("Existing product in a document updated")
                        continue upper
                    }
                }
                document.products.add(products)
                DevCycleLogger.log("adding product with code ${it.product_code}, belonging to company with id ${company.companyId}, to the current document")
                continue
            }
            DevCycleLogger.log("product with code ${it.product_code}, belonging to company with id ${company.companyId} not found, trying to save")
            products = save(it, company)
            if(!products)
            {
                DevCycleLogger.log("unable to save product with code ${it.product_code}, belonging to company with id ${company.companyId}, rejecting")
                response.rejectProduct(it)
                response.reportInvalidInput()
                continue
            }
            DevCycleLogger.log("product with code ${it.product_code}, belonging to company with id ${company.companyId} saved, adding to current document")
            document.products.add(products)
            products.save(true)
        }
        DevCycleLogger.log("exiting acceptProducts()")
        return response.getAsMap()
    }

    Map accept(AcceptanceDocumentCommand cmd)
    {
        DevCycleLogger.log("accept() called")
        Company company = Company.findWhere(token: cmd.companyToken)
        if(!company)
        {
            DevCycleLogger.log("company with token ${cmd.companyToken} not found, reporting authorization failure, exiting accept()")
            Response response = new Response()
            response.rejectCompanyToken()
            return response.getAsMap()
        }
        if(!cmd.validate())
        {
            DevCycleLogger.log("command object not validated, reporting invalid input, exiting accept()")
            Response response = new Response()
            response.reportInvalidInput()
            cmd.products.each {
                response.rejectProduct(it)
            }
            return response.getAsMap()
        }
        Document document = createAcceptanceDocumentMock(cmd)
        Map response = acceptProducts(document, company, cmd.products)
        if(document.validate())
        {
            document.save(true)
            DevCycleLogger.log("saving document with number ${document.documentNumber}, exiting accept()")
            return response
        }
        DevCycleLogger.log("document with number ${document.documentNumber} not saved(empty products list), exiting accept()")
        return response
    }

}
