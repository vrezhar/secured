package com.ttreport.api.current

import com.ttreport.api.resources.current.ExtendedProductCommand
import com.ttreport.api.resources.current.ProductCommand
import com.ttreport.data.BarCode
import com.ttreport.data.MarketEntranceBarCode
import com.ttreport.data.Company
import com.ttreport.data.Products
import com.ttreport.logs.DevCycleLogger
import grails.gorm.transactions.Transactional

@Transactional
class ProductsService extends ValidationErrorResolverService
{

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
        return new BarCode(uitCode: cmd.uit_code, uituCode: cmd.uitu_code, minified: cmd.minified)

    }

    BarCode update(ProductCommand cmd) throws Exception
    {
        DevCycleLogger.log('update() called')
        Products products = Products.get(cmd.id)
        BarCode barCode = initializeBarCode(cmd)
        barCode.products = products
        if(!barCode.save()) {
            DevCycleLogger.log_validation_errors(barCode,"bar code with uit code ${barCode.uitCode} and uitu code ${barCode.uituCode} not validated, nothing updated, exiting update()")
            throw new Exception("Bar code not saved")
        }
        products.addToBarCodes(barCode)
        products.save(true)
        DevCycleLogger.log("success, adding to the found product")
        return barCode
    }

    BarCode save(ProductCommand cmd, Company company) throws Exception
    {
        DevCycleLogger.log("save() called")
        DevCycleLogger.log("trying to save product with code ${cmd.id}, belonging to company with id ${company.companyId}")
        Products products = Products.findWhere(cost: cmd.cost, tax: cmd.tax, description: cmd.product_description)
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
        products = new Products(cost: cmd.cost, tax: cmd.tax, description: cmd.product_description)
        if(!products.validate()) {
            DevCycleLogger.log_validation_errors(products,"unable to validate the product, nothing saved, exiting save()")
            throw new Exception("Product not validated")
        }
        company.addToProducts(products)
        DevCycleLogger.log("product saved, trying to register a barcode")
        BarCode barCode = initializeBarCode(cmd)
        barCode.products = products
        products.addToBarCodes(barCode)
        products.save()
        if(!barCode.save(true)) {
            DevCycleLogger.log_validation_errors(barCode,"bar code with uit code ${barCode.uitCode} and uitu code ${barCode.uituCode} not validated, nothing saved, exiting save()")
            throw new Exception("Product not validated")
        }
        DevCycleLogger.log("bar code registered, product saved, exiting save()")
        return barCode
    }

    BarCode delete(ProductCommand cmd) throws Exception
    {
        DevCycleLogger.log("delete() called")
        DevCycleLogger.log("assuming that product corresponding to command object exists")
        Products products = Products.get(cmd.id)
        BarCode barCode = BarCode.findWhere(uitCode: cmd.uit_code?: null, uituCode: cmd.uitu_code?: null)
        if(!barCode) {
            DevCycleLogger.log("barcode with uit code ${cmd.uit_code} and uitu code ${cmd.uitu_code} not found, nothing updated, exiting ship()")
            throw new Exception("Barcode not found")
        }
        if(!products.hasBarcode(barCode)){
            DevCycleLogger.log("barcode with uit code ${cmd.uit_code} and uitu code ${cmd.uitu_code} not found in ownership of product ${products.id}, nothing updated, exiting ship()")
            throw new Exception("Bar code not owned by product")
        }
        barCode.dateDeleted = new Date()
        barCode.save(true)
        DevCycleLogger.log("shipping barcode with uit code ${barCode.uitCode} and uitu code ${barCode.uituCode}, saving changes, exiting ship()")
        return barCode
    }
}
