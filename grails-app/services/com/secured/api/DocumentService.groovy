package com.secured.api

import com.secured.api.resources.AcceptanceDocumentCommand
import com.secured.api.resources.ProductCommand
import com.secured.api.resources.ShipmentDocumentCommand
import com.secured.api.response.Response
import com.secured.data.Company
import com.secured.data.Products
import grails.gorm.transactions.Transactional

import static com.secured.api.resources.ProductCommand.*

@Transactional
class DocumentService
{
    ProductsService productsService

    Map accept(AcceptanceDocumentCommand cmd)
    {
        boolean  success = true
        Response response = new Response()
        Company company = Company.findWhere(token: cmd.companyToken)
        if(!company)
        {
            response.rejectCompanyToken()
            return response.getAsMap()
        }
        if(!cmd.validate())
        {
            cmd.products.each {
                response.rejectProduct(it)
            }
            return response.getAsMap()
        }
        cmd.products.each {
            if(!it.validate())
            {
                response.rejectProduct(it)
                success = false
            }
        }
        if(success)
        {
            AcceptanceDocumentCommand.createDocument(cmd)
        }
        return response.getAsMap()
    }


    Map ship(ShipmentDocumentCommand cmd)
    {
        boolean  success = true
        Response response = new Response()
        Company company = Company.findWhere(token: cmd.companyToken)
        if(!company)
        {
            response.rejectCompanyToken()
            return response.getAsMap()
        }
        if(!cmd.validate())
        {
            cmd.products.each {
                response.rejectProduct(it)
            }
            return response.getAsMap()
        }
        cmd.products.each {
            if(!it.validate())
            {
                response.rejectProduct(it)
                success = false
            }
            else
            {
                Products products = createOrUpdate(it)
                products.company = company
                products.save()
            }
        }
        if(success)
        {
            ShipmentDocumentCommand.createDocument(cmd)
        }
        return response.getAsMap()
    }
}
