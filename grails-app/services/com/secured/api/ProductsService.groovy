package com.secured.api

import com.secured.api.resources.ProductCommand
import com.secured.data.BarCode
import com.secured.data.Company
import com.secured.data.Products
import com.secured.logs.DevCycleLogger
import grails.gorm.transactions.Transactional

@Transactional
class ProductsService
{

    Products update(ProductCommand cmd, Company company, boolean ships = false)
    {
        DevCycleLogger.log('update() called')
        cmd.setAction("UPDATE")
        if(!cmd.validate())
        {
            DevCycleLogger.log("command object not validated, nothing updated, exiting update()")
            return null
        }
        Products products = Products.findWhere(productCode: cmd.product_code, company: company)
        if(products)
        {
            DevCycleLogger.log("found product with code ${it.product_code}, belonging to company with id ${company.companyId}(double checking just in case)")
            if(ships)
            {
                DevCycleLogger.log("product is to be shipped, trying to delete shipped barcodes")
                BarCode barCode = BarCode.findWhere(uit_code: cmd.uit_code, uitu_code: cmd.uitu_code, products: products)
                if(!barCode)
                {
                    DevCycleLogger.log("barcode with uit code ${barCode.uit_code} and uitu code ${barCode.uitu_code} not found, nothing updated, exiting update()")
                    return null
                }
                barCode.dateDeleted = new Date()
                barCode.save()
                products.save()
                DevCycleLogger.log("deleting barcode with uit code ${barCode.uit_code} and uitu code ${barCode.uitu_code}, saving changes, exiting update()")
                return products
            }
            products.description = (cmd.product_code != null && cmd.product_code != "") ? cmd.product_code : products.description
            if((cmd.uitu_code != null && cmd.uitu_code != "") || (cmd.uit_code != null && cmd.uit_code != ""))
            {
                DevCycleLogger.log("uitu or uit code detected, trying to register a barcode")
                BarCode barCode = new BarCode(uit_code: cmd.uit_code, uitu_code: cmd.uitu_code, products: products)
                if(!barCode.validate())
                {
                    DevCycleLogger.log("barcode with uit code ${barCode.uit_code} and uitu code ${barCode.uitu_code} not validated, nothing updated, exiting update()")
                    return null
                }
                products.addToBarCodes(barCode)
                DevCycleLogger.log("success, adding to the found product")
            }
            products.save()
            DevCycleLogger.log("saving changes, exiting update()")
            return products
        }
        DevCycleLogger.log("product with code ${products.productCode}, belonging to company with id ${company.companyId} not found(somehow), exiting update()")
        return null
    }

    Products save(ProductCommand cmd, Company company)
    {
        if(company == null)
        {
            DevCycleLogger.log("invoking save() on a null Company, exiting")
            return null
        }
        if(cmd == null)
        {
            DevCycleLogger.log("invoking save() on a null command, exiting()")
            return null
        }

        DevCycleLogger.log("save() called")
        cmd.setAction("SAVE")
        if(!cmd.validate())
        {
            DevCycleLogger.log("command object not validated, nothing saved, exiting save()")
            return null
        }
        DevCycleLogger.log("trying to save product with code ${products.productCode}, belonging to company with id ${company.companyId}")
        Products products = new Products(productCode: cmd.product_code, description: cmd.product_description, tax: cmd.tax, cost: cmd.cost, company: company)
        if(!products.validate())
        {
            DevCycleLogger.log("unable to save the product, nothing saved, exiting save()")
            return null
        }
        DevCycleLogger.log("product saved, trying to register a barcode")
        BarCode barCode = new BarCode(uit_code: cmd.uit_code, uitu_code: cmd.uitu_code, products: products)
        if(!barCode.validate())
        {
            DevCycleLogger.log("barcode with uit code ${barCode.uit_code} and uitu code ${barCode.uitu_code} not validated, nothing saved, exiting save()")
            return null
        }
        products.addToBarCodes(barCode)
        DevCycleLogger.log("barcode registered")
        products.save()
        DevCycleLogger.log("product saved, exiting save()")
        return products
    }

    boolean exists(String productCode, Company company)
    {
        if(company == null)
        {
            DevCycleLogger.log("invoking exit() on a null Company, exiting")
            return false
        }

        Products products = Products.findWhere(productCode: productCode, company: company)
        if(products)
            return true
        return false
    }

}
