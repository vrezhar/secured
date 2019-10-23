package com.ttreport.api.current

import com.ttreport.api.resources.current.AcceptanceDocumentCommand
import com.ttreport.api.resources.current.DocumentCommand
import com.ttreport.api.resources.current.ProductCommand
import com.ttreport.api.resources.current.ShipmentDocumentCommand
import com.ttreport.api.response.Responsive
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
        for(int i = 0; i < 3; ++i){
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
        int min = list[0]?: -1
        for(item in list){
            if(item < min){
                min = item
            }
        }
        return min
    }

    protected static void setActions(DocumentCommand cmd) throws Exception{
        if(cmd instanceof AcceptanceDocumentCommand){
            AcceptanceDocumentCommand doc = cmd as AcceptanceDocumentCommand
            for(product in doc.products){
                if(product.product_code){
                    product.setAction("UPDATE")
                    continue
                }
                product.setAction("SAVE")
            }
            return
        }
        if(cmd instanceof ShipmentDocumentCommand){
            ShipmentDocumentCommand doc = cmd as ShipmentDocumentCommand
            for(product in doc.products){
                product.setAction("DELETE")
            }
            return
        }
        throw new Exception("Invalid document")
    }

    protected Response performCommandValidation(DocumentCommand cmd){
        Response response = new Response()
        try{
            setActions(cmd)
        }
        catch (Exception ignored){
            response.status = getCode("command.document.invalid")
            return response
        }
        if(!cmd.validate()){
            for(error in cmd.errors.fieldErrors){
                if(error.field == "companyToken"){
                    response.rejectCompanyToken()
                    return response
                }
            }
            response.status = getCode("command.document.invalid")
            return response
        }
        Company company = Company.findWhere(token: cmd.companyToken)
        for(product in cmd.products){
            if(!product.validate()){
                response.rejectProduct(product).withReason(computeHighestPriorityError(product))
                product.rejected= true
                continue
            }
            if(product.action != "SAVE" && !company.has(Products.get(product.product_code))){
                response.rejectProduct(product).withReason(getCode('command.product.notfound'))
                product.rejected= true
            }
        }
        return response
    }

}
