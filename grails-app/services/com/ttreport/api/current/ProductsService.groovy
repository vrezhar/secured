package com.ttreport.api.current

import com.ttreport.api.resources.current.products.ExtendedProductCommand
import com.ttreport.api.resources.current.products.ProductCommand
import com.ttreport.api.resources.current.remains.ProductRemainsDescriptionCommand
import com.ttreport.api.resources.current.remains.ProductRemainsRegistryCommand
import com.ttreport.data.products.BarCode
import com.ttreport.data.products.MarketEntranceBarCode
import com.ttreport.data.Company
import com.ttreport.data.products.Products
import com.ttreport.logs.ServerLogger
import grails.gorm.transactions.Transactional

@Transactional
class ProductsService extends ValidationErrorResolverService
{

    protected static checkRejections(List<ProductCommand> products){
        for (item in products) {
            if (item.rejected) {
                return true
            }
        }
        return false
    }

    BarCode initializeBarCode(ProductCommand cmd)
    {
        if(cmd instanceof ExtendedProductCommand){
            ExtendedProductCommand command = cmd as ExtendedProductCommand
            BarCode barCode = new MarketEntranceBarCode(uitCode: cmd.uit_code, uituCode: cmd.uitu_code,
                    certificateDocumentNumber: command.certificate_document_number, certificateDocumentDate: command.certificate_document_number,
                    certificateDocument: command.certificate_document, tnvedCode: command.tnved_code, producerInn: command.producer_inn)
            if(command.owner_inn){
                barCode.ownerInn = command.owner_inn
            }
            if(command.production_date){
                barCode.productionDate = command.production_date
            }
            return barCode
        }
        return BarCode.findWhere(uitCode: cmd.uit_code, uituCode: cmd.uitu_code)?: new BarCode(uitCode: cmd.uit_code, uituCode: cmd.uitu_code)

    }

    BarCode update(ProductCommand cmd) throws Exception
    {
        ServerLogger.log('update() called')
        Products products = Products.get(cmd.id)
        if(cmd.product_description){
            products.description = cmd.product_description
        }
        if(cmd.cost){
            products.cost = cmd.cost
        }
        if(cmd.tax){
            products.tax = cmd.tax
        }
        BarCode barCode = initializeBarCode(cmd)
        barCode.products = products
        barCode.dateDeleted = null
        if(!barCode.save()) {
            ServerLogger.log_validation_errors(barCode,"bar code with uit code ${barCode.uitCode} and uitu code ${barCode.uituCode} not validated, nothing updated, exiting update()")
            throw new Exception("Bar code not saved")
        }
        products.addToBarCodes(barCode)
        products.save()
        ServerLogger.log("success, adding to the found product")
        return barCode
    }

    BarCode save(ProductCommand cmd, Company company) throws Exception
    {
        ServerLogger.log("save() called", "trying to save product with code ${cmd.id}, belonging to company with id ${company.inn}")
        Products products = Products.findWhere(cost: cmd.cost, tax: cmd.tax, description: cmd.product_description, company: company)
        if(products)
        {
            try {
                cmd.id = products.id
                BarCode barCode = update(cmd)
                return barCode
            }
            catch (Exception e){
                throw e
            }
        }
        products = new Products(cost: cmd.cost, tax: cmd.tax, description: cmd.product_description, company: company)
        if(!products.validate()) {
            ServerLogger.log_validation_errors(products,"unable to validate the product, nothing saved, exiting save()")
            throw new Exception("Product not validated")
        }
        ServerLogger.log("product saved, trying to register a barcode")
        BarCode barCode = initializeBarCode(cmd)
        barCode.dateDeleted = null
        barCode.products = products
        products.addToBarCodes(barCode)
        company.addToProducts(products)
        products.save()
        if(!barCode.save()) {
            ServerLogger.log_validation_errors(barCode,"bar code with uit code ${barCode.uitCode} and uitu code ${barCode.uituCode} not validated, nothing saved, exiting save()")
            throw new Exception("Product not validated")
        }
        ServerLogger.log("bar code registered, product saved, exiting save()")
        return barCode
    }

    BarCode delete(ProductCommand cmd) throws Exception
    {
        ServerLogger.log("delete() called", "assuming that product corresponding to command object exists")
        Products products = Products.get(cmd.id)
        if(cmd.product_description){
            products.description = cmd.product_description
        }
        if(cmd.cost){
            products.cost = cmd.cost
        }
        if(cmd.tax){
            products.tax = cmd.tax
        }
        BarCode barCode = BarCode.findWhere(uitCode: cmd.uit_code?: null, uituCode: cmd.uitu_code?: null)
        if(!barCode) {
            ServerLogger.log("barcode with uit code ${cmd.uit_code} and uitu code ${cmd.uitu_code} not found, nothing updated, exiting delete()")
            throw new Exception("Barcode not found")
        }
        if(!products.hasBarcode(barCode)){
            ServerLogger.log("barcode with uit code ${cmd.uit_code} and uitu code ${cmd.uitu_code} not found in ownership of product ${products.id}, nothing updated, exiting delete()")
            throw new Exception("Bar code not owned by product")
        }
        products.barCodes.remove(barCode)
        barCode.products = null
        barCode.dateDeleted = new Date()
        barCode.save()
        ServerLogger.log("shipping barcode with uit code ${barCode.uitCode} and uitu code ${barCode.uituCode}, saving changes, exiting delete()")
        return barCode
    }

    BarCode registerRemains(ProductRemainsRegistryCommand cmd)
    {
        try{
            BarCode barCode = BarCode.findWhere(uitCode: cmd.ki?: null, uituCode: cmd.kitu?: null)
            Products products = barCode?.products
            products?.cost = cmd.cost
            products?.tax = cmd.tax
            products?.description = cmd.description
            cmd.id = products.id
            products.save()
            return barCode
        }
        catch (Exception e){
            ServerLogger.log_exception(e)
            return null
        }
    }
}
