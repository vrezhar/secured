package com.ttreport.api.current

import com.ttreport.api.resources.current.ProductCommand
import com.ttreport.data.BarCode
import com.ttreport.data.Company
import com.ttreport.data.Products
import com.ttreport.logs.DevCycleLogger
import grails.gorm.transactions.Transactional

@Transactional
class ProductsService extends ValidationErrorResolverService
{

    Products update(ProductCommand cmd)
    {
        DevCycleLogger.log('update() called')
        cmd.setAction("UPDATE")
        if(!cmd.validate()) {
            DevCycleLogger.log_validation_errors(cmd,"command object not validated, nothing updated, exiting update()")
            return null
        }
        Products products = Products.get(cmd.product_code)
        BarCode barCode = new BarCode(uit_code: cmd.uit_code, uitu_code: cmd.uitu_code, products: products)
        if(!barCode.validate()) {
            DevCycleLogger.log_validation_errors(barCode,"barcode with uit code ${barCode.uit_code} and uitu code ${barCode.uitu_code} not validated, nothing updated, exiting update()")
            return null
        }
        barCode.save()
        products.addToBarCodes(barCode)
        products.save(true)
        DevCycleLogger.log("success, adding to the found product")
        return products
    }

    Products save(ProductCommand cmd, Company company)
    {
        DevCycleLogger.log("save() called")
        cmd.setAction("SAVE")
        if(!cmd.validate()) {
            DevCycleLogger.log_validation_errors(cmd,"command object not validated, nothing saved, exiting save()")
            return null
        }
        DevCycleLogger.log("trying to save product with code ${cmd.product_code}, belonging to company with id ${company.companyId}")
        Products products = new Products(description: cmd.product_description, tax: cmd.tax, cost: cmd.cost)
        //checking again just in case
        if(!products.validate()) {
            DevCycleLogger.log_validation_errors(products,"unable to validate the product, nothing saved, exiting save()")
            return null
        }
        company.addToProducts(products)
        DevCycleLogger.log("product saved, trying to register a barcode")
        BarCode barCode = new BarCode(uit_code: cmd.uit_code, uitu_code: cmd.uitu_code, products: products)
        if(!barCode.validate()) {
            DevCycleLogger.log_validation_errors(barCode,"barcode with uit code ${barCode.uit_code} and uitu code ${barCode.uitu_code} not validated, nothing saved, exiting save()")
            return null
        }
        products.addToBarCodes(barCode)
        products.save()
        barCode.save(true)
        DevCycleLogger.log("barcode registered, product saved, exiting save()")
        return products
    }

    Products delete(ProductCommand cmd, Products products)
    {
        cmd.setAction("DELETE")
        DevCycleLogger.log("delete() called")
        DevCycleLogger.log("assuming that product corresponding to command object exists")
        if(!cmd.validate()) {
            DevCycleLogger.log_validation_errors(cmd,"command object not validated, exiting delete()")
            return null
        }
//        Products products = Products.get(cmd.product_code)
        BarCode barCode = BarCode.findWhere(uit_code: cmd.uit_code, uitu_code: cmd.uitu_code)
        if(!barCode) {
            DevCycleLogger.log("barcode with uit code ${cmd.uit_code} and uitu code ${cmd.uitu_code} not found, nothing updated, exiting ship()")
            return null
        }
        if(!products.has(barCode)){
            DevCycleLogger.log("barcode with uit code ${cmd.uit_code} and uitu code ${cmd.uitu_code} not found in ownership of product ${products.id}, nothing updated, exiting ship()")
            return null
        }
        barCode.dateDeleted = new Date()
        barCode.save(true)
        DevCycleLogger.log("shipping barcode with uit code ${barCode.uit_code} and uitu code ${barCode.uitu_code}, saving changes, exiting ship()")
        return products
    }
}
