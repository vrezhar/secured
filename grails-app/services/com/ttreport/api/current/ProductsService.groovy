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

    Products update(ProductCommand cmd, long carry_id/*,Company company*/) throws Exception
    {
        DevCycleLogger.log('update() called')
        Products products = Products.get(cmd.product_code == 0 ? carry_id : cmd.product_code)
//        if(!cmd.product_code){
//            DevCycleLogger.log("No product code detected, redirecting to save")
//            cmd.setAction("SAVE")
//            try {
//                products = save(cmd,company)
//                return products
//            }
//            catch (Exception e){
//                throw e
//            }
//        }
        BarCode barCode = new BarCode(uit_code: cmd.uit_code, uitu_code: cmd.uitu_code, products: products)
        if(!barCode.save()) {
            DevCycleLogger.log_validation_errors(barCode,"barcode with uit code ${barCode.uit_code} and uitu code ${barCode.uitu_code} not validated, nothing updated, exiting update()")
            throw new Exception("Bar code not saved")
        }
        products.addToBarCodes(barCode)
        products.save(true)
        DevCycleLogger.log("success, adding to the found product")
        return products
    }

    Products save(ProductCommand cmd, Company company) throws Exception
    {
        DevCycleLogger.log("save() called")
        DevCycleLogger.log("trying to save product with code ${cmd.product_code}, belonging to company with id ${company.companyId}")
        Products products = Products.findWhere(cost: cmd.cost, tax: cmd.tax, description: cmd.product_description)
        if(products)
        {
            try {
                products = update(cmd, products.id/*,company*/)
                return products
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
        BarCode barCode = new BarCode(uit_code: cmd.uit_code, uitu_code: cmd.uitu_code, products: products)
        if(!barCode.validate()) {
            DevCycleLogger.log_validation_errors(barCode,"barcode with uit code ${barCode.uit_code} and uitu code ${barCode.uitu_code} not validated, nothing saved, exiting save()")
            throw new Exception("Product not validated")
        }
        products.addToBarCodes(barCode)
        products.save()
        barCode.save(true)
        DevCycleLogger.log("barcode registered, product saved, exiting save()")
        return products
    }

    Products delete(ProductCommand cmd) throws Exception
    {
        DevCycleLogger.log("delete() called")
        DevCycleLogger.log("assuming that product corresponding to command object exists")
        Products products = Products.get(cmd.product_code)
        BarCode barCode = BarCode.findWhere(uit_code: cmd.uit_code?: null, uitu_code: cmd.uitu_code?: null)
        if(!barCode) {
            DevCycleLogger.log("barcode with uit code ${cmd.uit_code} and uitu code ${cmd.uitu_code} not found, nothing updated, exiting ship()")
            throw new Exception("Barcode not validated")
        }
        if(!products.has(barCode)){
            DevCycleLogger.log("barcode with uit code ${cmd.uit_code} and uitu code ${cmd.uitu_code} not found in ownership of product ${products.id}, nothing updated, exiting ship()")
            throw new Exception("Bar code not owned by product")
        }
        barCode.dateDeleted = new Date()
        barCode.save(true)
        DevCycleLogger.log("shipping barcode with uit code ${barCode.uit_code} and uitu code ${barCode.uitu_code}, saving changes, exiting ship()")
        return products
    }
}
