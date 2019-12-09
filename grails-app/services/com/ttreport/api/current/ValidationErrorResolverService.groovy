package com.ttreport.api.current


import com.ttreport.api.resources.current.AcceptanceDocumentCommand
import com.ttreport.api.resources.current.DocumentCommand
import com.ttreport.api.resources.current.MarketEntranceCommand
import com.ttreport.api.resources.current.ProductCommand
import com.ttreport.api.resources.current.ReleaseCommand
import com.ttreport.api.resources.current.ShipmentDocumentCommand
import com.ttreport.data.products.BarCode
import com.ttreport.logs.ServerLogger
import com.ttreport.api.response.current.Response
import com.ttreport.data.Company
import com.ttreport.data.products.Products
import grails.gorm.transactions.Transactional
import org.springframework.context.MessageSource

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
        String error = getMessage(message)
        int result = 0
        for(int i = 0; i < error.length(); ++i){
            result = result*10 + error[i].toInteger()
        }
        return result
    }

    protected int computeHighestPriorityError(ProductCommand cmd)
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
        if(cmd instanceof AcceptanceDocumentCommand){
            AcceptanceDocumentCommand doc = cmd as AcceptanceDocumentCommand
            for(product in doc.products){
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
        if(cmd instanceof ShipmentDocumentCommand){
            ShipmentDocumentCommand doc = cmd as ShipmentDocumentCommand
            for(product in doc.products){
                ServerLogger.log("code ${product.uitu_code?: product.uit_code} of command object number ${product.id} set to be 'deleted'")
                product.setAction("DELETE")
            }
            return
        }
        if(cmd instanceof ReleaseCommand) {
            ReleaseCommand doc = cmd as ReleaseCommand
            for(product in doc.products){
                ServerLogger.log("code ${product.uitu_code?: product.uit_code} of command object number ${product.id} set to be 'deleted'")
                product.setAction("DELETE")
            }
            return
        }
        if(cmd instanceof MarketEntranceCommand){
            MarketEntranceCommand doc = cmd as MarketEntranceCommand
            for(product in doc.products){
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
            for(error in cmd.errors.fieldErrors){
                if(error.field == "companyToken"){
                    ServerLogger.log("authorisation failure")
                    response.rejectCompanyToken()
                    return response
                }
            }
            ServerLogger.log_validation_errors(cmd)
            response.status = 401
            return response
        }
        Company company = Company.findWhere(token: cmd.companyToken)
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
            BarCode barCode = BarCode.findWhere(uituCode: product.uitu_code ?: null, uitCode: product.uit_code ?: null)
            if(barCode && product.action == "DELETE"){
                if(!company.hasBarCode(barCode)){
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
}
