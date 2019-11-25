package com.ttreport.api.current

import com.ttreport.api.resources.current.AcceptanceDocumentCommand
import com.ttreport.api.resources.current.DocumentAndResponse
import com.ttreport.api.resources.current.FromPhysCommand
import com.ttreport.api.resources.current.MarketEntranceCommand
import com.ttreport.api.resources.current.ReleaseCommand
import com.ttreport.api.resources.current.ShipmentDocumentCommand
import com.ttreport.api.response.current.Response
import com.ttreport.data.BarCode
import com.ttreport.data.Company
import com.ttreport.data.Products
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.data.documents.differentiated.existing.RFIEntranceDocument
import com.ttreport.logs.DevCycleLogger
import grails.gorm.transactions.Transactional

@Transactional
class ProductsManagerService extends DocumentService
{

    protected DocumentAndResponse acceptProducts(AcceptanceDocumentCommand cmd)
    {
        DevCycleLogger.log("acceptProducts() called")
        DocumentAndResponse dandr = new DocumentAndResponse()
        Response response = performCommandValidation(cmd)
        if(response.status == 400 || response.status == 401){
            dandr.response = response.getAsMap()
            return  dandr
        }
        Company company = Company.findWhere(token: cmd.companyToken)
        Document document = createAcceptanceDocumentMock(cmd)
        document.company = company
        for(item in cmd.products) {
            if(!item.rejected)
            {
                if (item.action == "UPDATE") {
                    DevCycleLogger.log("found product with code ${item.id}, belonging to company with id ${company.companyId}")
                    BarCode barCode
                    try{
                        barCode = update(item)
                    }
                    catch(Exception e){
                        DevCycleLogger.log(e.message)
                        dandr.response = response.getAsMap()
                        dandr.response.status = 500
                        return dandr
                    }
                    document.addToBarCodes(barCode) // necessary,belongsTo in BarCode is not defined
                    item.id = barCode.products.id
                    response.accept(item)
                    DevCycleLogger.log("adding product with code ${item.id}, belonging to company with id ${company.companyId}, to the current document")
                    continue
                }

                DevCycleLogger.log("product with code ${item.id}, belonging to company with id ${company.companyId} not found, trying to save")
                BarCode barCode
                try{
                    barCode = save(item, company)
                }
                catch (Exception e){
                    DevCycleLogger.log(e.message)
                    dandr.response = response.getAsMap()
                    dandr.response.status = 500
                    return dandr
                }
                document.addToBarCodes(barCode) // necessary,belongsTo in BarCode is not defined
                item.id = barCode.products.id
                DevCycleLogger.log("product with code ${item.id}, belonging to company with id ${company.companyId} saved, adding to current document")
                response.accept(item)
            }
        }
        if(!document.barCodes || document.barCodes?.size() == 0)
        {
            dandr.response = response.getAsMap()
            return dandr
        }
        company.addToDocuments(document)
        dandr.document = document
        dandr.response = response.getAsMap()
        return dandr
    }

    protected DocumentAndResponse shipProducts(ShipmentDocumentCommand cmd)
    {
        DocumentAndResponse dandr = new DocumentAndResponse()
        Response response = performCommandValidation(cmd)
        if(response.status == 400 || response.status == 401){
            dandr.response = response.getAsMap()
            return  dandr
        }
        Company company = Company.findWhere(token: cmd.companyToken)
        Document document = createShipmentDocumentMock(cmd)

        for(item in cmd.products) {
            if(!item.rejected){
                BarCode barCode
                try{
                    barCode = delete(item)
                }
                catch (Exception e){
                    DevCycleLogger.log(e.message)
                    dandr.response = response.getAsMap()
                    dandr.response.status = 500
                    return dandr
                }
                document.addToBarCodes(barCode)
                item.id = barCode.products.id
                DevCycleLogger.log("found product with code ${item.id}, belonging to company with id ${company.companyId}, trying to delete")
                response.accept(item)
            }
        }
        if(!document.barCodes || document.barCodes?.size() == 0)
        {
            dandr.response = response.getAsMap()
            return dandr
        }
        DevCycleLogger.log("exiting shipProducts()")
        company.addToDocuments(document)
        dandr.document = document
        dandr.response = response.getAsMap()
        return dandr
    }

    protected DocumentAndResponse enterProductsIntoMarket(MarketEntranceCommand cmd)
    {
        DevCycleLogger.log("enterProductsIntoMarket() called")
        DocumentAndResponse dandr = new DocumentAndResponse()
        Response response = performCommandValidation(cmd)
        if(response.status == 400 || response.status == 401){
            dandr.response = response.getAsMap()
            return  dandr
        }
        Company company = Company.findWhere(token: cmd.companyToken)
        Document document = createMarketEntranceDocumentMock(cmd)
        for(item in cmd.products) {
            if(!item.rejected)
            {
                if (item.action == "UPDATE") {
                    DevCycleLogger.log("found product with code ${item.id}, belonging to company with id ${company.companyId}")
                    BarCode barCode
                    try{
                        barCode = update(item)
                    }
                    catch(Exception e){
                        DevCycleLogger.log(e.message)
                        dandr.response = response.getAsMap()
                        dandr.response.status = 500
                        return dandr
                    }
                    document.addToBarCodes(barCode) // necessary,belongsTo in BarCode is not defined
                    item.id = barCode.products.id
                    response.accept(item)
                    DevCycleLogger.log("adding product with code ${item.id}, belonging to company with id ${company.companyId}, to the current document")
                    continue
                }

                DevCycleLogger.log("product with code ${item.id}, belonging to company with id ${company.companyId} not found, trying to save")
                BarCode barCode
                try{
                    barCode = save(item, company)
                }
                catch (Exception e){
                    DevCycleLogger.log(e.message)
                    dandr.response = response.getAsMap()
                    dandr.response.status = 500
                    return dandr
                }
                document.addToBarCodes(barCode) // necessary,belongsTo in BarCode is not defined
                item.id = barCode.products.id
                DevCycleLogger.log("product with code ${item.id}, belonging to company with id ${company.companyId} saved, adding to current document")
                response.accept(item)
            }
        }

        if(!document.barCodes || document.barCodes?.size() == 0)
        {
            dandr.response = response.getAsMap()
            return dandr
        }
        DevCycleLogger.log("exiting enterProductsIntoMarket()")
        company.addToDocuments(document)
        dandr.document = document
        dandr.response = response.getAsMap()
        return dandr
    }

    protected DocumentAndResponse releaseProducts(ReleaseCommand cmd)
    {
        DocumentAndResponse dandr = new DocumentAndResponse()
        Response response = performCommandValidation(cmd)
        if(response.status == 400 || response.status == 401){
            dandr.response = response.getAsMap()
            return  dandr
        }
        Company company = Company.findWhere(token: cmd.companyToken)
        Document document = createReleaseDocumentMock(cmd)
        for(item in cmd.products) {
            if(!item.rejected){
                BarCode barCode
                try{
                    barCode = delete(item)
                }
                catch (Exception e){
                    DevCycleLogger.log(e.message)
                    dandr.response.status = 500
                    return dandr
                }
                document.addToBarCodes(barCode)
                item.id = barCode.products.id
                DevCycleLogger.log("found product with code ${item.id}, belonging to company with id ${company.companyId}, trying to delete")
                response.accept(item)
            }
        }
        dandr.response = response.getAsMap()
        if(!document.barCodes || document.barCodes?.size() == 0) {
            return dandr
        }
        DevCycleLogger.log("exiting shipProducts()")
        company.addToDocuments(document)
        dandr.document = document
        dandr.response = response.getAsMap()
        return  dandr
    }

    protected DocumentAndResponse enterFPP(FromPhysCommand cmd)
    {
        DevCycleLogger.log("enterfpp() called")
        DocumentAndResponse dandr = new DocumentAndResponse()
        Response response = new Response()
        Document document = new RFIEntranceDocument(participantInn: cmd.participant_inn)
        if(!cmd.validate(['companyToken'])) {
            dandr.response = [status: 400]
            return  dandr
        }
        if(!cmd.validate(['products_list'])) {
            dandr.response = [status: 401]
            return  dandr
        }
        Company company = Company.findWhere(token: cmd.companyToken)
        for(product in cmd.products_list){
            if(product.id || Products.findWhere(description: product.product_description, tax: product.tax, cost: product.cost, company: company)){
                product.action = "UPDATE"
            }
            else {
                product.action = "SAVE"
            }
            if(!product.validate()){
                response.reportInvalidInput()
                DevCycleLogger.log("ProductCommand object not validated, rejecting")
                response.rejectProduct(product,computeHighestPriorityError(product))
                product.rejected = true
                continue
            }
            if(product.action != "SAVE" && !company.hasProduct(Products.get(product.id))){
                DevCycleLogger.log("Product doesn't belong to found company's product list")
                response.reportInvalidInput()
                product.errors.rejectValue('id','command.product.notfound')
                response.rejectProduct(product,computeHighestPriorityError(product))
                product.rejected = true
            }
            if(product.action == "UPDATE") {
                DevCycleLogger.log("found product with code ${product.id}, belonging to company with id ${company.companyId}")
                BarCode barCode
                try{
                    barCode = update(product)
                }
                catch(Exception e){
                    DevCycleLogger.log(e.message)
                    dandr.response = response.getAsMap()
                    dandr.response.status = 500
                    return dandr
                }
                document.addToBarCodes(barCode) // necessary,belongsTo in BarCode is not defined
                product.id = barCode.products.id
                response.accept(product)
                DevCycleLogger.log("adding product with code ${product.id}, belonging to company with id ${company.companyId}, to the current document")
                continue
            }

            DevCycleLogger.log("product with code ${product.id}, belonging to company with id ${company.companyId} not found, trying to save")
            BarCode barCode
            try{
                barCode = save(product, company)
            }
            catch (Exception e){
                DevCycleLogger.log(e.message)
                dandr.response = response.getAsMap()
                dandr.response.status = 500
                return dandr
            }
            document.addToBarCodes(barCode) // necessary,belongsTo in BarCode is not defined
            product.id = barCode.products.id
            DevCycleLogger.log("product with code ${product.id}, belonging to company with id ${company.companyId} saved, adding to current document")
            response.accept(product)
        }
        dandr.response = response.getAsMap()
        if(!document.barCodes || document.barCodes?.size() == 0) {
            return dandr
        }
        DevCycleLogger.log("exiting enterFPP()")
        company.addToDocuments(document)
        dandr.document = document
        dandr.response = response.getAsMap()
        return  dandr
    }
//    private DocumentAndResponse doInitialValidation(DocumentCommand cmd)
//    {
//        DocumentAndResponse dandr = new DocumentAndResponse()
//        Response response = performCommandValidation(cmd)
//        if(response.status == 400 || response.status == 402){
//            dandr.response = response.getAsMap()
//            return null
//        }
//        return dandr
//    }

//    private static DocumentAndResponse doFinalValidationAndReturn(DocumentAndResponse dandr, Company company)
//    {
//        Document document = dandr.document
//        if(!document.barCodes || document.barCodes?.size() == 0) {
//            return dandr
//        }
//        company.addToDocuments(document)
//        dandr.document = document
//        return  dandr
//    }
//
//    private doDelete(Document document, List<ProductCommand> products, Response response)
//    {
//
//    }
//
//    private doSaveOrUpdate(Document document, List<ProductCommand> products, Response response, Company company)
//    {
//
//    }
}
