package com.ttreport.api.current

import com.ttreport.MessageBundleService
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
class ValidationErrorResolverService extends MessageBundleService
{
    static scope = 'prototype'

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
        Company company = cmd.authorize()
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
        Company company = cmd.authorize()
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
        Company company = cmd.authorize()
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
