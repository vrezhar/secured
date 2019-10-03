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

    Products update(ProductCommand cmd)
    {
        DevCycleLogger.log('update() called')
        cmd.setAction("UPDATE")
        if(!cmd.validate())
        {
            cmd.errors.fieldErrors.each {
                DevCycleLogger.log("couldn't validated value ${it.rejectedValue} for field ${it.field}")
            }
            DevCycleLogger.log("command object not validated, nothing updated, exiting update()")
            return null
        }
        Products products = Products.findWhere(productCode: cmd.product_code)
        boolean descriptionchanged = false
        if(cmd.product_description != null && cmd.product_description != "" && cmd.product_description != products.description)
        {
            DevCycleLogger.log("updating found product's description")
            products.description = cmd.product_description
            descriptionchanged = true
        }
        if((cmd.uitu_code != null && cmd.uitu_code != "") || (cmd.uit_code != null && cmd.uit_code != ""))
        {
            DevCycleLogger.log("uitu or uit code detected, trying to register a barcode")
            BarCode barCode = new BarCode(uit_code: cmd.uit_code, uitu_code: cmd.uitu_code, products: products)
            if(!barCode.validate())
            {
                barCode.errors.fieldErrors.each {
                    DevCycleLogger.log("couldn't validate value ${it.rejectedValue} for field ${it.field}")
                }
                if(descriptionchanged)
                {
                    products.save(true)
                    return null
                }
                DevCycleLogger.log("barcode with uit code ${barCode.uit_code} and uitu code ${barCode.uitu_code} not validated, nothing updated, exiting update()")
                return null
            }
            products.save()
            barCode.save(true)
            DevCycleLogger.log("success, adding to the found product")
            return products
        }
        products.save(true)
        DevCycleLogger.log("saving changes, exiting update()")
        return products
    }

    Products save(ProductCommand cmd, Company company)
    {
        DevCycleLogger.log("save() called")
        cmd.setAction("SAVE")
        if(!cmd.validate())
        {
            cmd.errors.fieldErrors.each {
                DevCycleLogger.log("couldn't validate value ${it.rejectedValue} for field ${it.field}")
            }
            DevCycleLogger.log("command object not validated, nothing saved, exiting save()")
            return null
        }
        DevCycleLogger.log("trying to save product with code ${cmd.product_code}, belonging to company with id ${company.companyId}")
        Products products = new Products(productCode: cmd.product_code, description: cmd.product_description, tax: cmd.tax, cost: cmd.cost, company: company)
        if(!products.validate())
        {
            products.errors.fieldErrors.each {
                DevCycleLogger.log("couldn't validate value ${it.rejectedValue} for field ${it.field}")
            }
            DevCycleLogger.log("unable to validate the product, nothing saved, exiting save()")
            return null
        }
        DevCycleLogger.log("product saved, trying to register a barcode")
        BarCode barCode = new BarCode(uit_code: cmd.uit_code, uitu_code: cmd.uitu_code, products: products)
        if(!barCode.validate())
        {
            barCode.errors.fieldErrors.each {
                DevCycleLogger.log("couldn't validate value ${it.rejectedValue} for field ${it.field}")
            }
            DevCycleLogger.log("barcode with uit code ${barCode.uit_code} and uitu code ${barCode.uitu_code} not validated, nothing saved, exiting save()")
            return null
        }
        products.save()
        barCode.save(true)
        //products.addToBarCodes(barCode) //unnecessary
        //company.addToProducts(products)
        DevCycleLogger.log("barcode registered")
        DevCycleLogger.log("product saved, exiting save()")
        return products
    }

    Products ship(ProductCommand cmd)
    {
        DevCycleLogger.log("ship() called")
        if(!cmd.validate())
        {
            cmd.errors.fieldErrors.each {
                DevCycleLogger.log("${it.rejectedValue} not validated for field ${it.field}")
            }
            DevCycleLogger.log("command object not validated, exiting ship()")
            return null
        }
        Products products = Products.findWhere(productCode: cmd.product_code)
        BarCode barCode = BarCode.findWhere(uit_code: cmd.uit_code, uitu_code: cmd.uitu_code, products: products)
        if(!barCode)
        {
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
