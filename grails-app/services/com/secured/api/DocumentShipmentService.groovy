package com.secured.api

import com.secured.api.resources.ProductCommand
import com.secured.api.resources.ShipmentDocumentCommand
import com.secured.api.response.Response
import com.secured.data.Company
import com.secured.data.Document
import com.secured.data.Products
import com.secured.logs.DevCycleLogger
import grails.gorm.transactions.Transactional

@Transactional
class DocumentShipmentService  extends DocumentService
{

    protected Map shipProducts(Document document, Company company, List<ProductCommand> productsList)
    {
        Response response = new Response()
        for(it in productsList)
        {
            Products products = Products.findWhere(productCode:  it.product_code)
            if(products && products?.company?.products?.contains(products))
            {
                DevCycleLogger.log("found product with code ${it.product_code}, belonging to company with id ${company.companyId}")
                products = ship(it)
                if(!products)
                {
                    DevCycleLogger.log("couldn't update product with code ${it.product_code}, belonging to company with id ${company.companyId}, rejecting")
                    response.rejectProduct(it)
                    response.reportInvalidInput()
                    continue
                }
                products.save()
                document.products.add(products)
                continue
            }
            DevCycleLogger.log("couldn't find product with code ${it.product_code}, belonging to company with id ${company.companyId}, rejecting")
            response.rejectProduct(it)
            response.reportInvalidInput()
        }
        DevCycleLogger.log("exiting shipProducts()")
        return response.getAsMap()
    }


    Map ship(ShipmentDocumentCommand cmd)
    {
        DevCycleLogger.log("ship() called")
        Company company = Company.findWhere(token: cmd.companyToken)
        if(!company)
        {
            DevCycleLogger.log("company with token ${cmd.companyToken} not found, reporting authorization failure, exiting ship()")
            Response response = new Response()
            response.rejectCompanyToken()
            return response.getAsMap()
        }
        if(!cmd.validate())
        {
            DevCycleLogger.log("command object not validated, reporting invalid input, exiting ship()")
            Response response = new Response()
            response.reportInvalidInput()
            cmd.products.each {
                response.rejectProduct(it)
            }
            return response.getAsMap()
        }
        Document document = createShipmentDocumentMock(cmd)
        Map response = shipProducts(document, company, cmd.products)
        if(!document.products.isEmpty())
        {
            if(!document.validate())
            {
                document.errors.fieldErrors.each {
                    DevCycleLogger.log("couldn't validate value ${it.rejectedValue} for field ${it.field}")
                }
                DevCycleLogger.log("document with number ${document.documentNumber} not validated, exiting accept()")
                return response
            }
            document.save(true)
            DevCycleLogger.log("saving document with number ${document.documentNumber}, exiting accept()")
            return response
        }
        DevCycleLogger.log("document with number ${document.documentNumber} not saved(empty products list), exiting accept()")
        return response
    }
}
