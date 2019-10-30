package com.ttreport.api.deprecated

import com.ttreport.api.resources.deprecated.BarCodeRegisteringSource
import com.ttreport.api.response.Responsive
import com.ttreport.data.BarCode
import com.ttreport.data.Company
import com.ttreport.data.Products
import com.ttreport.logs.DevCycleLogger
import grails.gorm.transactions.Transactional

@Transactional
class BarCodeService extends Responsive
{

    def save(BarCodeRegisteringSource src)
    {
        DevCycleLogger.log("BarCodeService called, method: save()")
        Map response = [:]
        response.status = statusCodes.success
        response.barcode_list = []

        if(!src.validate())
        {
            DevCycleLogger.log("barcode command object not validated, exiting save()")
            response.status = statusCodes.invalid_input
            response.barcode_list = src.barcodes
            return response
        }
        if(src.productCode == null || src.productCode == "")
        {
            DevCycleLogger.log("barcode command object lacks product code, exiting save()")
            response.status = statusCodes.invalid_input
            response.barcode_list = src.barcodes
            return response
        }

        Company owner = Company.findWhere(token: src.companyToken)
        if(!owner)
        {
            DevCycleLogger.log("company with token ${src.companyToken} not found, exiting save()")
            response.status = statusCodes.invalid_token
            response.barcode_list = src.barcodes
            return response
        }

        Products products = new Products(productCode: src.productCode, company: owner)

        DevCycleLogger.log("trying to create new product with code ${src.productCode}...")
        products.description = src.productDescription ?: ""
        if(!products.validate())
        {
            DevCycleLogger.log("product with code ${products.productCode} already exists, no changes made to the database, exiting save()")
            response.status = statusCodes.invalid_input
            response.barcode_list = src.barcodes
            return response
        }
        DevCycleLogger.log("Product created")
        products.save()
        owner.addToProducts(products)
        DevCycleLogger.log("product saved as property of ${owner.companyId} at ${owner.address}")
        response.product_id = products.id

        for(barcode in src.barcodes)
        {
            BarCode barCode = new BarCode(code: barcode, products: products)
            if(!barCode.validate())
            {
                response.barcode_list.add(barcode)
                DevCycleLogger.log("Barcode ${barcode} not validated")
            }
            else
            {
                barCode.save()
                products.addToBarCodes(barCode)
                DevCycleLogger.log("Barcode ${barcode} assigned to product type ${products.productCode}")
            }
        }
        products.save()
        owner.save()

        if(response.barcode_list == [])
        {
            DevCycleLogger.log("saving changes, report success, exiting save()")
        }
        else
        {
            DevCycleLogger.log("report failure, exiting delete()")
            response.status = statusCodes.invalid_input
        }
        return response
    }

    def update(BarCodeRegisteringSource src)
    {
        DevCycleLogger.log("BarCodeService called, method: update()")
        Map response = [:]
        response.barcode_list = []
        response.status = statusCodes.success

        Company owner = Company.findWhere(token: src.companyToken)
        if(!owner)
        {
            DevCycleLogger.log("company with token ${src.companyToken} not found, exiting update()")
            response.status = statusCodes.invalid_token
            response.barcode_list = src.barcodes
            return response
        }

        Products products = Products.get(src.productId)
        if(!products)
        {
            DevCycleLogger.log("product with id ${src.productId} not found, exiting update()")
            response.status = statusCodes.invalid_input
            response.barcode_list = src.barcodes
            return response
        }
        if(!owner.has(products))
        {
            DevCycleLogger.log("product with id ${src.productId} not found in possession of: ${owner.companyId} at ${owner.address}, exiting update()")
            response.status = statusCodes.invalid_input
            response.barcode_list = src.barcodes
            return response
        }
        DevCycleLogger.log("found product ${products.productCode} with id ${src.productId}")

        if(src.productDescription != null || src.productDescription != "")
        {
            DevCycleLogger.log("updating description of product ${products.productCode} with id ${src.productId}")
            products.description = src.productDescription
        }

        for(barcode in src.barcodes)
        {
            BarCode barCode = new BarCode(code: barcode, products: products)
            if(!barCode.validate())
            {
                DevCycleLogger.log("Barcode ${barcode} not validated, company trying to register: ${owner.companyId} at ${owner.address}")
                response.barcode_list.add(barcode)
            }
            else
            {
                barCode.save()
                products.addToBarCodes(barCode)
                DevCycleLogger.log("assigned barcode ${barcode} to product type ${products.productCode}, registered by ${owner.companyId} at ${owner.address}")
            }
        }

        products.save()
        owner.save()

        if(src.barcodes == null || src.barcodes == [])
        {
            DevCycleLogger.log("no barcodes supplied, exiting update()")
        }
        else if(response.barcode_list == [])
        {
            DevCycleLogger.log("report success, exiting update()")
        }
        else
        {
            DevCycleLogger.log("report failure, exiting update()")
            response.status = statusCodes.invalid_input
        }
        return response
    }

    def delete(BarCodeRegisteringSource src)
    {
        DevCycleLogger.log("BarCodeService called, method: delete()")
        Map response = [:]
        response.barcode_list = []
        response.status = statusCodes.success

        Company owner = Company.findWhere(token: src.companyToken)
        if(!owner)
        {
            DevCycleLogger.log("company with token ${src.companyToken} not found, exiting delete()")
            response.status = statusCodes.invalid_token
            response.barcode_list = src.barcodes
            return response
        }

        Products products = Products.get(src.productId)
        if(!products)
        {
            DevCycleLogger.log("product with id ${src.productId} not found, exiting delete()")
            response.status = statusCodes.invalid_input
            response.barcode_list = src.barcodes
            return response
        }
        if(!owner.has(products))
        {
            DevCycleLogger.log("product with id ${src.productId} not found in possession of: ${owner.companyId} at ${owner.address}, exiting delete()")
            response.status = statusCodes.invalid_input
            response.barcode_list = src.barcodes
            return response
        }


        for(barCode in src.barcodes)
        {
            BarCode barcode = BarCode.findWhere(code: barCode, products: products)
            if(!barcode)
            {
                DevCycleLogger.log("barcode ${barCode} not found")
                response.barcode_list.add(barCode)
                continue
            }
            if(barcode.dateDeleted != null)
            {
                DevCycleLogger.log("barcode ${barCode} already deleted")
                response.barcode_list.add(barCode)
                continue
            }
            DevCycleLogger.log("deleting ${barCode}, requested by ${owner.companyId} at ${owner.address}")
            barcode.dateDeleted = new Date()
            barcode.save()
        }

        if(src.barcodes == null || src.barcodes == [])
        {
            DevCycleLogger.log("no barcodes supplied, exiting delete()")
            response.status = statusCodes.invalid_input
        }
        else if(response.barcode_list == [])
        {
            DevCycleLogger.log("report success, exiting delete()")
        }
        else
        {
            DevCycleLogger.log("report failure, exiting delete()")
            response.status = statusCodes.invalid_input
        }
        return response
    }
}
