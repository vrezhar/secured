package com.ttreport.api.current

import com.ttreport.api.DocumentCommandObject
import com.ttreport.api.resources.current.documents.AcceptanceDocumentCommand
import com.ttreport.api.resources.current.documents.DocumentCommand
import com.ttreport.api.resources.current.documents.MarketEntranceCommand
import com.ttreport.api.resources.current.documents.remains.RemainsDescriptionDocumentCommand
import com.ttreport.api.resources.current.documents.remains.RemainsRegistryDocumentCommand
import com.ttreport.api.resources.current.products.ProductCommand
import com.ttreport.api.resources.current.documents.ReleaseCommand
import com.ttreport.api.resources.current.documents.ShipmentDocumentCommand
import com.ttreport.api.resources.current.remains.ProductRemainsRegistryCommand
import com.ttreport.data.products.BarCode
import com.ttreport.logs.ServerLogger
import com.ttreport.api.response.current.Response
import com.ttreport.data.Company
import com.ttreport.data.products.Products
import grails.gorm.transactions.Transactional
import grails.validation.Validateable
import org.springframework.context.MessageSource

import javax.activation.CommandObject

@Transactional
class ValidationErrorResolverService
{
    static scope = 'prototype'
    private static final Map<String, Locale> langLocaleMappings = [
            'en': Locale.ENGLISH,
            'ru': new Locale('ru', 'RU'),
    ].asImmutable()

    MessageSource messageSource

    protected String getMessage(String message) {
        messageSource.getMessage(message, [].toArray(), langLocaleMappings.en)
    }

    protected int getCode(String message)
    {
        if(message == 'nullable'){
            return 413
        }
        String error
        try{
            error = getMessage(message)
        }
        catch (Exception ignored){
            return -1
        }
        int result = 0
        for(int i = 0; i < error.length(); ++i){
            result = result*10 + error[i].toInteger()
        }
        return result
    }

    protected int computeHighestPriorityError(Validateable cmd)
    {
        List<Integer> list = []
        cmd.errors.fieldErrors.each {
            list.add(getCode(it.code))
        }
        ServerLogger.log_validation_errors(cmd)
        int min = list[0]?: -1
        for(item in list){
            if(item < min){
                min = item
            }
        }
        return min
    }

    protected static void setActions(DocumentCommand cmd) throws Exception{
        ServerLogger.log("Determining actions for command objects")
        if(cmd instanceof AcceptanceDocumentCommand || cmd instanceof MarketEntranceCommand){
            for(product in cmd.products){
                if(product.id){
                    ServerLogger.log("Command object number ${product.id} set to be updated with ${product.uitu_code?: product.uit_code}")
                    product.setAction("UPDATE")
                    continue
                }
                ServerLogger.log("Command object with description ${product.product_description} set to be saved")
                product.setAction("SAVE")
            }
            return
        }
        if(cmd instanceof ShipmentDocumentCommand || cmd instanceof ReleaseCommand){
            for(product in cmd.products){
                ServerLogger.log("code ${product.uitu_code?: product.uit_code} of command object number ${product.id} set to be 'deleted'")
                product.setAction("DELETE")
            }
            return
        }
//        if(cmd instanceof ReleaseCommand) {
//            ReleaseCommand doc = cmd as ReleaseCommand
//            for(product in doc.products){
//                ServerLogger.log("code ${product.uitu_code?: product.uit_code} of command object number ${product.id} set to be 'deleted'")
//                product.setAction("DELETE")
//            }
//            return
//        }
//        if(cmd instanceof MarketEntranceCommand){
//            MarketEntranceCommand doc = cmd as MarketEntranceCommand
//            for(product in doc.products){
//                if(product.id){
//                    ServerLogger.log("Command object number ${product.id} set to be updated with ${product.uitu_code?: product.uit_code}")
//                    product.setAction("UPDATE")
//                    continue
//                }
//                ServerLogger.log("Command object with description ${product.product_description} set to be saved")
//                product.setAction("SAVE")
//            }
//            return
//        }
        throw new Exception("Invalid document")
    }


    protected Response performCommandValidation(DocumentCommand cmd){
        boolean hasErrors = false
        ServerLogger.log("Starting validation process")
        Response response = new Response()
        try{
            setActions(cmd)
        }
        catch (Exception e){
            ServerLogger.log(e.message)
            response.status = 401
            return response
        }
        if(!cmd.validate()){
            ServerLogger.log("DocumentCommand object not validated")
            response.reportInvalidInput()
            ServerLogger.log_validation_errors(cmd)
            response.status = 401
            return response
        }
        Company company = authorize(cmd)
        if(!company){
            ServerLogger.log("invalid token")
            response.status = 400
            return response
        }
        for(product in cmd.products){
            if(!product.validate()){
                response.reportInvalidInput()
                ServerLogger.log("ProductCommand object not validated, rejecting")
                response.rejectProduct(product,computeHighestPriorityError(product))
                product.rejected = true
                continue
            }
            if(product.action != "SAVE" && !company.hasProduct(Products.get(product.id))){
                response.reportInvalidInput()
                ServerLogger.log("Product doesn't belong to found company's product list")
                product.errors.rejectValue('id','command.product.notfound')
                response.rejectProduct(product,computeHighestPriorityError(product))
                product.rejected = true
            }

            if(product.action == "DELETE"){
                BarCode barCode = BarCode.findWhere(uituCode: product.uitu_code ?: null, uitCode: product.uit_code ?: null)
                if(barCode && !company.hasBarCode(barCode)){
                    response.reportInvalidInput()
                    ServerLogger.log("Product doesn't belong to found company's product list")
                    product.errors.rejectValue('id','command.code.notfound')
                    response.rejectProduct(product,computeHighestPriorityError(product))
                    product.rejected = true
                }
            }
        }
        return response
    }

    Company authorize(DocumentCommandObject cmd)
    {
        return Company.findWhere(token: cmd.getCompanyToken())
    }

    protected Response validateRemainsDescription(RemainsDescriptionDocumentCommand cmd)
    {
        Response response = new Response()
        if(!cmd.validate()){
            ServerLogger.log("DocumentCommand object not validated")
            response.reportInvalidInput()
            ServerLogger.log_validation_errors(cmd)
            response.status = 401
            return response
        }
        Company company = authorize(cmd)
        if(!company){
            ServerLogger.log("invalid token")
            response.status = 400
            return response
        }

        return response
    }

    protected Response validateRemainsRegistry(RemainsRegistryDocumentCommand cmd)
    {
        Response response = new Response()
        if(!cmd.validate()){
            ServerLogger.log("DocumentCommand object not validated")
            response.reportInvalidInput()
            ServerLogger.log_validation_errors(cmd)
            response.status = 401
            return response
        }
        Company company = authorize(cmd)
        if(!company){
            ServerLogger.log("invalid token")
            response.status = 400
            return response
        }
        for(product in cmd.products){
            if(!cmd.validate()){
                response.rejectProduct(product, computeHighestPriorityError(product))
            }
            BarCode barCode = BarCode.findWhere(uituCode: product.ki ?: null, uitCode: product.kitu ?: null)
            if(barCode && !company.hasBarCode(barCode)){
                response.reportInvalidInput()
                ServerLogger.log("Product doesn't belong to found company's product list")
                product.errors.rejectValue('id','command.code.notfound')
                response.rejectProduct(product,computeHighestPriorityError(product))
                product.rejected = true
            }
        }
        return response
    }
}
