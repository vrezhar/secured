package com.secured.api

import com.secured.api.resources.AcceptanceDocumentCommand
import com.secured.api.resources.DocumentCommand
import com.secured.api.resources.ProductCommand
import com.secured.api.resources.ShipmentDocumentCommand
import com.secured.api.response.Response
import com.secured.data.Company
import com.secured.data.Document
import com.secured.data.Products
import grails.gorm.transactions.Transactional

@Transactional
class DocumentService
{
    static scope = 'prototype'

    ProductsService productsService

    protected static Document find(String token)
    {
        Company company = Company.findWhere(token: token)
        if(!company)
            return null
        return Document.findWhere(company: company)
    }

    protected static Document createMock(DocumentCommand cmd)
    {
        Document document = new Document()
        document.documentDate = cmd.document_date
        document.transferDate = cmd.transfer_date
        document.turnoverType = cmd.turnover_type
        document.documentNumber = cmd.document_number
        return document
    }

    protected static Document createAcceptanceDocument(AcceptanceDocumentCommand cmd)
    {
        Document document = createMock(cmd)
        document.requestType = "ACCEPTANCE"
        document.releaseOrderNumber = cmd.release_order_number
        document.acceptanceDate = cmd.acceptance_date
        document.tradeSenderInn = cmd.trade_sender_inn
        document.tradeOwnerInn = cmd.trade_owner_inn
        document.tradeSenderName = cmd.trade_sender_name
        document.tradeOwnerName = cmd.trade_owner_name
        document.tradeRecipientInn = cmd.trade_recipient_inn
        return document
    }

    protected static Document createShipmentDocument(ShipmentDocumentCommand cmd)
    {
        Document document = createMock(cmd)
        document.requestType = "SHIPMENT"
        if(cmd.pdf != "" && cmd.pdf != null)
            document.pdf = cmd.pdf
        document.owner = cmd.owner
        document.ownerInn = cmd.owner_inn
        document.receiver = cmd.receiver
        document.receiverInn = cmd.receiver_inn
        document.sender = cmd.sender
        document.senderInn = cmd.sender_inn
        return document
    }

    protected Map acceptProducts(Document document, List<ProductCommand> productsList)
    {
        Response response = new Response()
        for(it in productsList)
        {
            if(productsService.exists(it.product_code, document.company))
            {
                Products products = productsService.update(it, document.company)
                if(!products)
                {
                    response.rejectProduct(it)
                    break
                }
                document.products.add(products)
                break
            }
            Products products = productsService.save(it, document.company)
            if(!products)
            {
                response.rejectProduct(it)
                break
            }
            document.products.add(products)
        }
        return response.getAsMap()
    }

    protected Map shipProducts(Document document, List<ProductCommand> productsList)
    {
        Response response = new Response()
        for(it in productsList)
        {
            if(productsService.exists(it.product_code, document.company))
            {
                Products products = productsService.update(it, document.company, true)
                if(!products)
                {
                    response.rejectProduct(it)
                    break
                }
                document.products.add(products)
                break
            }
            response.rejectProduct(it)
        }
        return response.getAsMap()
    }

    Map accept(AcceptanceDocumentCommand cmd)
    {
        Company company = Company.findWhere(token: cmd.companyToken)
        if(!company)
        {
            Response response = new Response()
            response.rejectCompanyToken()
            return response.getAsMap()
        }
        if(!cmd.validate())
        {
            Response response = new Response()
            cmd.products.each {
                response.rejectProduct(it)
            }
            return response.getAsMap()
        }
        Document document = createAcceptanceDocument(cmd)
        document.company = company
        Map response = acceptProducts(document,cmd.products)
        document.save()
        return response
    }

    Map ship(ShipmentDocumentCommand cmd)
    {
        Company company = Company.findWhere(token: cmd.companyToken)
        if(!company)
        {
            Response response = new Response()
            response.rejectCompanyToken()
            return response.getAsMap()
        }
        if(!cmd.validate())
        {
            Response response = new Response()
            cmd.products.each {
                response.rejectProduct(it)
            }
            return response.getAsMap()
        }
        Document document = createShipmentDocument(cmd)
        document.company = company
        Map response = shipProducts(document,cmd.products)
        return response
    }
}
