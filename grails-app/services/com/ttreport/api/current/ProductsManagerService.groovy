package com.ttreport.api.current

import com.ttreport.api.current.existing.DocumentService
import com.ttreport.api.resources.current.DocumentAndResponse
import com.ttreport.api.resources.current.documents.*
import com.ttreport.api.resources.current.documents.remains.RemainsDescriptionDocumentCommand
import com.ttreport.api.resources.current.documents.remains.RemainsRegistryDocumentCommand
import com.ttreport.api.response.current.Response
import com.ttreport.data.Company
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.data.documents.differentiated.existing.RFIEntranceDocument
import com.ttreport.data.documents.differentiated.remains.RemainsDescriptionDocument
import com.ttreport.data.documents.differentiated.remains.RemainsDocument
import com.ttreport.data.documents.differentiated.remains.RemainsRegistryDocument
import com.ttreport.data.products.BarCode
import com.ttreport.data.products.Products
import com.ttreport.data.products.remains.FullRemainsProduct
import com.ttreport.data.products.remains.RemainsProduct
import com.ttreport.logs.ServerLogger
import grails.gorm.transactions.Transactional
import org.springframework.transaction.TransactionStatus

@Transactional
class ProductsManagerService extends DocumentService
{

    protected DocumentAndResponse acceptProducts(AcceptanceDocumentCommand cmd)
    {
        ServerLogger.log("acceptProducts() called")
        DocumentAndResponse dandr = new DocumentAndResponse()
        Response response = performCommandValidation(cmd)
        if(response.status == 400 || response.status == 401 || checkRejections(cmd.products)){
            dandr.response = response.getAsMap()
            return  dandr
        }
        Company company = Company.findWhere(token: cmd.companyToken)
        Document document = createAcceptanceDocumentMock(cmd)
        document.company = company
        for(item in cmd.products) {
            if (item.action == "UPDATE") {
                ServerLogger.log("found product with code ${item.id}, belonging to company with inn ${company.inn}")
                BarCode barCode
                try{
                    barCode = update(item)
                }
                catch(Exception e){
                    ServerLogger.log_exception(e)
                    dandr.response = response.getAsMap()
                    dandr.response.status = 500
                    return dandr
                }
                document.addToBarCodes(barCode) // necessary,belongsTo in BarCode is not defined
                item.id = barCode.products.id
                response.accept(item)
                ServerLogger.log("adding product with code ${item.id}, belonging to company with id ${company.inn}, to the current document")
                continue
            }

            ServerLogger.log("product with code ${item.id}, belonging to company with id ${company.inn} not found, trying to save")
            BarCode barCode
            try{
                barCode = save(item, company)
            }
            catch (Exception e){
                ServerLogger.log_exception(e)
                dandr.response = response.getAsMap()
                dandr.response.status = 500
                return dandr
            }
            document.addToBarCodes(barCode) // necessary,belongsTo in BarCode is not defined
            item.id = barCode.products.id
            ServerLogger.log("product with code ${item.id}, belonging to company with id ${company.inn} saved, adding to current document")
            response.accept(item)
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
        if(response.status == 400 || response.status == 401 || checkRejections(cmd.products)){
            dandr.response = response.getAsMap()
            return  dandr
        }
        Company company = Company.findWhere(token: cmd.companyToken)
        Document document = createShipmentDocumentMock(cmd)

        for(item in cmd.products) {
            BarCode barCode
            try{
                barCode = delete(item)
            }
            catch (Exception e){
                ServerLogger.log_exception(e)
                dandr.response = response.getAsMap()
                dandr.response.status = 500
                return dandr
            }
            document.addToBarCodes(barCode)
//            item.id = barCode.products.id
            ServerLogger.log("found product with code ${item.id}, belonging to company with id ${company.inn}, trying to delete")
            response.accept(item)
        }
        if(!document.barCodes || document.barCodes?.size() == 0)
        {
            dandr.response = response.getAsMap()
            return dandr
        }
        ServerLogger.log("exiting shipProducts()")
        company.addToDocuments(document)
        dandr.document = document
        dandr.response = response.getAsMap()
        return dandr
    }

    protected DocumentAndResponse enterProductsIntoMarket(MarketEntranceCommand cmd)
    {
        ServerLogger.log("enterProductsIntoMarket() called")
        DocumentAndResponse dandr = new DocumentAndResponse()
        Response response = performCommandValidation(cmd)
        if(response.status == 400 || response.status == 401 || checkRejections(cmd.products)){
            dandr.response = response.getAsMap()
            return  dandr
        }
        Company company = Company.findWhere(token: cmd.companyToken)
        Document document = createMarketEntranceDocumentMock(cmd)
        for(item in cmd.products) {
            if (item.action == "UPDATE") {
                ServerLogger.log("found product with code ${item.id}, belonging to company with id ${company.inn}")
                BarCode barCode
                try{
                    barCode = update(item)
                }
                catch(Exception e){
                    ServerLogger.log_exception(e)
                    dandr.response = response.getAsMap()
                    dandr.response.status = 500
                    return dandr
                }
                document.addToBarCodes(barCode) // necessary,belongsTo in BarCode is not defined
                item.id = barCode.products.id
                response.accept(item)
                ServerLogger.log("adding product with code ${item.id}, belonging to company with id ${company.inn}, to the current document")
                continue
            }

            ServerLogger.log("product with code ${item.id}, belonging to company with id ${company.inn} not found, trying to save")
            BarCode barCode
            try{
                barCode = save(item, company)
            }
            catch (Exception e){
                ServerLogger.log_exception(e)
                dandr.response = response.getAsMap()
                dandr.response.status = 500
                return dandr
            }
            document.addToBarCodes(barCode) // necessary,belongsTo in BarCode is not defined
            item.id = barCode.products.id
            ServerLogger.log("product with code ${item.id}, belonging to company with id ${company.inn} saved, adding to current document")
            response.accept(item)
        }

        if(!document.barCodes || document.barCodes?.size() == 0)
        {
            dandr.response = response.getAsMap()
            return dandr
        }
        ServerLogger.log("exiting enterProductsIntoMarket()")
        company.addToDocuments(document)
        dandr.document = document
        dandr.response = response.getAsMap()
        return dandr
    }

    protected DocumentAndResponse releaseProducts(ReleaseCommand cmd)
    {
        DocumentAndResponse dandr = new DocumentAndResponse()
        Response response = performCommandValidation(cmd)
        if(response.status == 400 || response.status == 401 || checkRejections(cmd.products)){
            dandr.response = response.getAsMap()
            return  dandr
        }
        Company company = Company.findWhere(token: cmd.companyToken)
        Document document = createReleaseDocumentMock(cmd)
        for(item in cmd.products) {
            BarCode barCode
            try{
                barCode = delete(item)
            }
            catch (Exception e){
                ServerLogger.log_exception(e)
                dandr.response.status = 500
                return dandr
            }
            document.addToBarCodes(barCode)
            item.id = barCode.products.id
            ServerLogger.log("found product with code ${item.id}, belonging to company with id ${company.inn}, trying to delete")
            response.accept(item)
        }
        dandr.response = response.getAsMap()
        if(!document.barCodes || document.barCodes?.size() == 0) {
            return dandr
        }
        ServerLogger.log("exiting shipProducts()")
        company.addToDocuments(document)
        dandr.document = document
        dandr.response = response.getAsMap()
        return  dandr
    }

    protected DocumentAndResponse enterFPP(FromPhysCommand cmd)
    {
        ServerLogger.log("enterfpp() called")
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
        boolean shouldSave = true
        for(product in cmd.products_list) {
            if (product.id || Products.findWhere(description: product.product_description, tax: product.tax, cost: product.cost, company: company)) {
                product.action = "UPDATE"
            } else {
                product.action = "SAVE"
            }
            if(!product.validate()){
                response.reportInvalidInput()
                ServerLogger.log("ProductCommand object not validated, rejecting")
                response.rejectProduct(product,computeHighestPriorityError(product))
                product.rejected = true
                shouldSave = false
                continue
            }
            if(product.action != "SAVE" && !company.hasProduct(Products.get(product.id))){
                ServerLogger.log("Product doesn't belong to found company's product list")
                response.reportInvalidInput()
                product.errors.rejectValue('id','command.product.notfound')
                response.rejectProduct(product,computeHighestPriorityError(product))
                shouldSave = false
                product.rejected = true
            }
        }
        if(shouldSave) {
            for (product in cmd.products_list) {
                if(product.action == "UPDATE") {
                    ServerLogger.log("found product with code ${product.id}, belonging to company with id ${company.inn}")
                    BarCode barCode
                    try{
                        barCode = update(product)
                    }
                    catch(Exception e){
                        ServerLogger.log_exception(e)
                        dandr.response = response.getAsMap()
                        dandr.response.status = 500
                        return dandr
                    }
                    document.addToBarCodes(barCode) // necessary,belongsTo in BarCode is not defined
                    product.id = barCode.products.id
                    response.accept(product)
                    ServerLogger.log("adding product with code ${product.id}, belonging to company with id ${company.inn}, to the current document")
                    continue
                }

                ServerLogger.log("product with code ${product.id}, belonging to company with id ${company.inn} not found, trying to save")
                BarCode barCode
                try{
                    barCode = save(product, company)
                }
                catch (Exception e){
                    ServerLogger.log_exception(e)
                    dandr.response = response.getAsMap()
                    dandr.response.status = 500
                    return dandr
                }
                document.addToBarCodes(barCode) // necessary,belongsTo in BarCode is not defined
                product.id = barCode.products.id
                ServerLogger.log("product with code ${product.id}, belonging to company with id ${company.inn} saved, adding to current document")
                response.accept(product)
            }
        }
        dandr.response = response.getAsMap()
        if(!document.barCodes || document.barCodes?.size() == 0) {
            return dandr
        }
        ServerLogger.log("exiting enterFPP()")
        company.addToDocuments(document)
        dandr.document = document
        dandr.response = response.getAsMap()
        return  dandr
    }

    DocumentAndResponse describeRemains(RemainsDescriptionDocumentCommand cmd)
    {
        DocumentAndResponse documentAndResponse = new DocumentAndResponse()
        Company company = cmd.authorize()
        if(!company){
            documentAndResponse.response = [status: 400]
            return documentAndResponse
        }
        if(!cmd.validate()){
            documentAndResponse.response = [status: 401]
            return documentAndResponse
        }
        Map response = [:]
        response.status = 200
        RemainsDocument document = new RemainsDescriptionDocument()
        document.company = company
        Document.withTransaction { TransactionStatus status ->
            boolean save = true
            List errorList = [], acceptedList = []
            for(product in cmd.products){
                if(!product.validate()){
                    save = false
                    List errors = []
                    product.errors.fieldErrors.each {
                        errors.add([field: it.field, error: getMessage(it.code)?: 'invalid value'])
                    }
                    errorList.add([product: product.product_name?: product.description, errors: errors])
                    continue
                }
                Products products
                def bindingMap = [
                        company: company,
                        model: product.model,
                        productName: product.product_name,
                        brand: product.brand,
                        country: product.country,
                        productType: product.product_type,
                        materialUpper: product.material_upper,
                        materialLining: product.material_lining,
                        materialDown: product.material_down,
                        color: product.color,
                        productSize: product.product_size,
                        tnvedCode2: product.tnved_code_2,
                        productGender: product.product_gender,
                        releaseMethod: product.release_method,
                        certificateDate: product.certificate_date,
                        certificateNumber: product.certificate_number,
                        certificateType :product.certificate_type
                ]
                if(product.description){
                    bindingMap.description = product.description
                }
                if(!product.description && product.product_name){
                    bindingMap.description = product.product_name
                }
                if(product.tax){
                    bindingMap.tax = product.tax
                }
                if(product.cost){
                    bindingMap.cost = product.cost
                }
                if(product.description_type == 'EXTENDED'){
                    products = FullRemainsProduct.findOrSaveWhere(bindingMap)
                    Map newProduct = [product_name: product.product_name, id: products.id]
                    if(!acceptedList.contains(newProduct)){
                        acceptedList.add(newProduct)
                    }
                }
                else{
                    products = RemainsProduct.findOrSaveWhere(bindingMap)
                    Map newProduct = [product_name: product.identifier?: product.description, id: products.id]
                    if(!acceptedList.contains(newProduct)){
                        acceptedList.add(newProduct)
                    }
                }
                document.addToProducts(products)
            }
            if(!save){
                response.status = 402
                response.errors = errorList
                status.setRollbackOnly()
                return
            }
            response.accepted = acceptedList
            document.save()
            documentAndResponse.document = document
            return
        }
        return documentAndResponse
    }

    DocumentAndResponse registerRemains(RemainsRegistryDocumentCommand cmd)
    {
        DocumentAndResponse documentAndResponse = new DocumentAndResponse()
        Company company = cmd.authorize()
        if(!company){
            documentAndResponse.response = [status: 400]
            return documentAndResponse
        }
        if(!cmd.validate()){
            documentAndResponse.response = [status: 401]
            return documentAndResponse
        }
        Map response = [:]
        response.status = 200
        Document document = new RemainsRegistryDocument()
        document.company = company
        Document.withTransaction { TransactionStatus status ->
            boolean save = true
            List errorList = [], acceptedList = []
            for (product in cmd.products) {
                if(!product.validate()){
                    save = false
                    List errors = []
                    product.errors.fieldErrors.each {
                        errors.add([field: it.field, error: getMessage(it.code)?: 'invalid value'])
                    }
                    errorList.add([codes: [ki: product.ki, kitu: product.kitu], errors: errors])
                    continue
                }
                BarCode barCode = BarCode.findWhere(uitCode: product.ki?: null, uituCode: product.kitu?: null)
                document.addToBarCodes(barCode)
            }
            if(!save){
                response.status = 402
                response.errors = errorList
                status.setRollbackOnly()
                return documentAndResponse
            }
            response.accepted = acceptedList
            document.save()
            documentAndResponse.document = document
            return documentAndResponse
        }
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
