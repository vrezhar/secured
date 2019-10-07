package com.secured.api.current

import com.secured.api.resources.current.ProductCommand
import com.secured.data.BarCode
import com.secured.data.Company
import com.secured.data.Products
import com.secured.logs.DevCycleLogger
import grails.gorm.transactions.Transactional
import grails.validation.Validateable

@Transactional
class ProductsService
{

    Products update(ProductCommand cmd)
    {
        DevCycleLogger.log('update() called')
        cmd.setAction("UPDATE")
        if(!cmd.validate()) {
            DevCycleLogger.log_validation_errors(cmd,"command object not validated, nothing updated, exiting update()")
            return null
        }
        Products products = Products.findWhere(productCode: cmd.product_code)
        if(cmd.product_description  && cmd.product_description != products.description) {
            DevCycleLogger.log("updating found product's description")
            products.description = cmd.product_description
        }
        if(cmd.uitu_code || cmd.uit_code) {
            DevCycleLogger.log("uitu or uit code detected, trying to register a barcode")
            BarCode barCode = new BarCode(uit_code: cmd.uit_code, uitu_code: cmd.uitu_code, products: products)
            if(barCode.validate()) {
                products.addToBarCodes(barCode)
                products.save()
                barCode.save(true)
                DevCycleLogger.log("success, adding to the found product")
                return products
            }
            DevCycleLogger.log_validation_errors(barCode as Validateable,"barcode with uit code ${barCode.uit_code} and uitu code ${barCode.uitu_code} not validated, nothing updated, exiting update()")
            return null
        }
        DevCycleLogger.log("No codes found, exiting update()")
        return null
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
        Products products = new Products(productCode: cmd.product_code, description: cmd.product_description, tax: cmd.tax, cost: cmd.cost, company: company)
        if(!products.validate()) {
            DevCycleLogger.log_validation_errors(products as Validateable,"unable to validate the product, nothing saved, exiting save()")
            return null
        }
        DevCycleLogger.log("product saved, trying to register a barcode")
        BarCode barCode = new BarCode(uit_code: cmd.uit_code, uitu_code: cmd.uitu_code, products: products)
        if(!barCode.validate()) {
            DevCycleLogger.log_validation_errors(barCode as Validateable, "barcode with uit code ${barCode.uit_code} and uitu code ${barCode.uitu_code} not validated, nothing saved, exiting save()")
            return null
        }
        products.addToBarCodes(barCode)
        products.save()
        barCode.save(true)
        DevCycleLogger.log("barcode registered, product saved, exiting save()")
        return products
    }

    Products delete(ProductCommand cmd)
    {
        DevCycleLogger.log("delete() called")
        DevCycleLogger.log("assuming that product corresponding to command object exists")
        if(!cmd.validate()) {
            DevCycleLogger.log_validation_errors(cmd,"command object not validated, exiting delete()")
            return null
        }
        Products products = Products.findWhere(productCode: cmd.product_code)
        BarCode barCode = BarCode.findWhere(uit_code: cmd.uit_code, uitu_code: cmd.uitu_code, products: products)
        if(!barCode) {
            DevCycleLogger.log("barcode with uit code ${barCode.uit_code} and uitu code ${barCode.uitu_code} not found, nothing updated, exiting ship()")
            return null
        }
        barCode.dateDeleted = new Date()
        barCode.save()
        products.save(true)
        DevCycleLogger.log("shipping barcode with uit code ${barCode.uit_code} and uitu code ${barCode.uitu_code}, saving changes, exiting ship()")
        return products
    }

}
