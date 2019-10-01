package com.secured.api

import com.secured.api.resources.AcceptanceDocumentCommand
import com.secured.api.resources.DocumentCommand
import com.secured.api.resources.ProductCommand
import com.secured.api.resources.ShipmentDocumentCommand
import com.secured.api.response.Response
import com.secured.data.Company
import com.secured.data.Document
import com.secured.data.Products
import com.secured.logs.DevCycleLogger
import grails.gorm.transactions.Transactional

@Transactional
class DocumentService
{
    static scope = 'prototype'

    ProductsService productsService

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
        DevCycleLogger.log("acceptProducts() called")
        Response response = new Response()
        for(it in productsList)
        {
            println(it.product_code)
            println(document.company.companyId)
            if(productsService.exists(it.product_code, document.company))
            {
                DevCycleLogger.log("found product with code ${it.product_code}, belonging to company with id ${document.company.companyId}")
                Products products = productsService.update(it, document.company)
                if(!products)
                {
                    DevCycleLogger.("unable to update product with code ${it.product_code}, belonging to company with id ${document.company.companyId}, rejecting")
                    response.rejectProduct(it)
                    response.reportInvalidInput()
                    continue
                }
                document.products.add(products)
                DevCycleLogger.log("adding product with code ${it.product_code}, belonging to company with id ${document.company.companyId}, to the current document")
                continue
            }
            DevCycleLogger.log("product with code ${it.product_code}, belonging to company with id ${document.company.companyId} not found, trying to save")
            Products products = productsService.save(it, document.company)
            if(!products)
            {
                DevCycleLogger.log("unable to save product with code ${it.product_code}, belonging to company with id ${document.company.companyId}, rejecting")
                response.rejectProduct(it)
                response.reportInvalidInput()
                continue
            }
            DevCycleLogger.log("product with code ${it.product_code}, belonging to company with id ${document.company.companyId} saved, adding to current document")
            document.products.add(products)
        }
        DevCycleLogger.log("exiting acceptProducts()")
        return response.getAsMap()
    }

    protected Map shipProducts(Document document, List<ProductCommand> productsList)
    {
        Response response = new Response()
        for(it in productsList)
        {
            if(productsService.exists(it.product_code, document.company))
            {
                DevCycleLogger.log("found product with code ${it.product_code}, belonging to company with id ${document.company.companyId}")
                Products products = productsService.update(it, document.company, true)
                if(!products)
                {
                    DevCycleLogger.log("couldn't update product with code ${it.product_code}, belonging to company with id ${document.company.companyId}, rejecting")
                    response.rejectProduct(it)
                    response.reportInvalidInput()
                    continue
                }
                document.products.add(products)
                continue
            }
            DevCycleLogger.log("couldn't find product with code ${it.product_code}, belonging to company with id ${document.company.companyId}, rejecting")
            response.rejectProduct(it)
            response.reportInvalidInput()
        }
        DevCycleLogger.log("exiting shipProducts()")
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
        Document document = createAcceptanceDocument(cmd)
        document.company = company
        Map response = acceptProducts(document,cmd.products)
        document.save()
        DevCycleLogger.log("saving document with number ${document.documentNumber}, exiting accept()")
        return response
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
        Document document = createShipmentDocument(cmd)
        document.company = company
        Map response = shipProducts(document,cmd.products)
        document.save()
        DevCycleLogger.log("saving document with number ${document.documentNumber}, exiting ship()")
        return response
    }
}
