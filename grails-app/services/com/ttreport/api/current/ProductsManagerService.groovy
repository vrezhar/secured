package com.ttreport.api.current

import com.ttreport.api.resources.current.AcceptanceDocumentCommand
import com.ttreport.api.resources.current.DocumentAndResponse
import com.ttreport.api.resources.current.ShipmentDocumentCommand
import com.ttreport.api.response.current.Response
import com.ttreport.data.BarCode
import com.ttreport.data.Company
import com.ttreport.data.Document
import com.ttreport.data.Products
import com.ttreport.logs.DevCycleLogger
import grails.gorm.transactions.Transactional

@Transactional
class ProductsManagerService extends DocumentService{

    protected DocumentAndResponse acceptProducts(AcceptanceDocumentCommand cmd)
    {
        DevCycleLogger.log("acceptProducts() called")
        DocumentAndResponse dandr = new DocumentAndResponse()
        Response response = performCommandValidation(cmd)
        if(response.status == 400 || response.status == 402){
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
        if(response.status == 400 || response.status == 402){
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
}
