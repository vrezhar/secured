package com.ttreport.api.current

import com.ttreport.api.resources.current.AcceptanceDocumentCommand
import com.ttreport.api.resources.current.DocumentCommand
import com.ttreport.api.resources.current.ProductCommand
import com.ttreport.api.resources.current.ShipmentDocumentCommand
import com.ttreport.logs.DevCycleLogger
import com.ttreport.api.response.current.Response
import com.ttreport.data.Company
import com.ttreport.data.Products
import grails.gorm.transactions.Transactional

@Transactional
class ValidationErrorResolverService
{

    protected static String getMessage(String message) {
        def props = new Properties()
        new File("grails-app/i18n/messages.properties").withInputStream {
            stream -> props.load(stream)
        }
        return props[message]
    }

    protected int getCode(String message)
    {
        String error = getMessage(message)
        if(error.length() != 3){
            return -1;
        }
        int result = 0
        for(literal in error){
            result = result*10 + literal.toInteger()
        }
        return result
    }

    protected int computeHighestPriorityError(ProductCommand cmd)
    {
        List<Integer> list = []
        cmd.errors.fieldErrors.each {
            list.add(getCode(it.code))
        }
        DevCycleLogger.log_validation_errors(cmd)
        int min = list[0]?: -1
        for(item in list){
            if(item < min){
                min = item
            }
        }
        return min
    }

    protected static void setActions(DocumentCommand cmd) throws Exception{
        DevCycleLogger.log("Determining actions for command objects")
        if(cmd instanceof AcceptanceDocumentCommand){
            AcceptanceDocumentCommand doc = cmd as AcceptanceDocumentCommand
            for(product in doc.products){
                if(product.id){
                    DevCycleLogger.log("Command object number ${product.id} set to be updated")
                    product.setAction("UPDATE")
                    continue
                }
                DevCycleLogger.log("Command object with description ${product.product_description} set to be saved")
                product.setAction("SAVE")
            }
            return
        }
        if(cmd instanceof ShipmentDocumentCommand){
            ShipmentDocumentCommand doc = cmd as ShipmentDocumentCommand
            for(product in doc.products){
                DevCycleLogger.log("Command object number ${product.id} set to be 'deleted'")
                product.setAction("DELETE")
            }
            return
        }
        throw new Exception("Invalid document")
    }

    protected Response performCommandValidation(DocumentCommand cmd){
        DevCycleLogger.log("Starting validation process")
        Response response = new Response()
        try{
            setActions(cmd)
        }
        catch (Exception e){
            DevCycleLogger.log(e.message)
            response.status = getCode("command.document.invalid")
            return response
        }
        if(!cmd.validate()){
            DevCycleLogger.log("DocumentCommand object not validated")
            response.reportInvalidInput()
            for(error in cmd.errors.fieldErrors){
                if(error.field == "companyToken"){
                    DevCycleLogger.log("authorisation failure")
                    response.rejectCompanyToken()
                    return response
                }
            }
            DevCycleLogger.log_validation_errors(cmd)
            response.status = getCode("command.document.invalid")
            return response
        }
        Company company = Company.findWhere(token: cmd.companyToken)
        for(product in cmd.products){
            if(!product.validate()){
                response.reportInvalidInput()
                DevCycleLogger.log("ProductCommand object not validated, rejecting")
                response.rejectProduct(product,computeHighestPriorityError(product))
                product.rejected = true
                continue
            }
            if(product.action != "SAVE" && !company.has(Products.get(product.id))){
                DevCycleLogger.log("Product doesn't belong to found company's product list")
                response.rejectProduct(product,computeHighestPriorityError(product))
                product.rejected = true
            }
        }
        return response
    }

}
