package com.ttreport.api.deprecated

import com.ttreport.api.resources.deprecated.BarCodeRegisteringSource
import com.ttreport.configuration.ApplicationConfiguration
import com.ttreport.data.products.BarCode
import com.ttreport.data.Company
import com.ttreport.data.products.Products
import com.ttreport.logs.ServerLogger
import grails.gorm.transactions.Transactional

@Transactional
class BarCodeService extends ApplicationConfiguration
{

    def save(BarCodeRegisteringSource src)
    {
        ServerLogger.log("BarCodeService called, method: save()")
        Map response = [:]
        response.status = apiStatusCodes.success
        response.barcode_list = []

        if(!src.validate())
        {
            ServerLogger.log("barcode command object not validated, exiting save()")
            response.status = apiStatusCodes.invalid_input
            response.barcode_list = src.barcodes
            return response
        }
        if(src.productCode == null || src.productCode == "")
        {
            ServerLogger.log("barcode command object lacks product code, exiting save()")
            response.status = apiStatusCodes.invalid_input
            response.barcode_list = src.barcodes
            return response
        }

        Company owner = Company.findWhere(token: src.companyToken)
        if(!owner)
        {
            ServerLogger.log("company with token ${src.companyToken} not found, exiting save()")
            response.status = apiStatusCodes.invalid_token
            response.barcode_list = src.barcodes
            return response
        }

        Products products = new Products(productCode: src.productCode, company: owner)

        ServerLogger.log("trying to create new product with code ${src.productCode}...")
        products.description = src.productDescription ?: ""
        if(!products.validate())
        {
            ServerLogger.log("product with code ${products.id} already exists, no changes made to the database, exiting save()")
            response.status = apiStatusCodes.invalid_input
            response.barcode_list = src.barcodes
            return response
        }
        ServerLogger.log("Product created")
        products.save()
        owner.addToProducts(products)
        ServerLogger.log("product saved as property of ${owner.companyId} at ${owner.address}")
        response.product_id = products.id

        for(barcode in src.barcodes)
        {
            BarCode barCode = new BarCode(code: barcode, products: products)
            if(!barCode.validate())
            {
                response.barcode_list.add(barcode)
                ServerLogger.log("Barcode ${barcode} not validated")
            }
            else
            {
                barCode.save()
                products.addToBarCodes(barCode)
                ServerLogger.log("Barcode ${barcode} assigned to product type ${products.id}")
            }
        }
        products.save()
        owner.save()

        if(response.barcode_list == [])
        {
            ServerLogger.log("saving changes, report success, exiting save()")
        }
        else
        {
            ServerLogger.log("report failure, exiting delete()")
            response.status = apiStatusCodes.invalid_input
        }
        return response
    }

    def update(BarCodeRegisteringSource src)
    {
        ServerLogger.log("BarCodeService called, method: update()")
        Map response = [:]
        response.barcode_list = []
        response.status = apiStatusCodes.success

        Company owner = Company.findWhere(token: src.companyToken)
        if(!owner)
        {
            ServerLogger.log("company with token ${src.companyToken} not found, exiting update()")
            response.status = apiStatusCodes.invalid_token
            response.barcode_list = src.barcodes
            return response
        }

        Products products = Products.get(src.productId)
        if(!products)
        {
            ServerLogger.log("product with id ${src.productId} not found, exiting update()")
            response.status = apiStatusCodes.invalid_input
            response.barcode_list = src.barcodes
            return response
        }
        if(!owner.hasProduct(products))
        {
            ServerLogger.log("product with id ${src.productId} not found in possession of: ${owner.companyId} at ${owner.address}, exiting update()")
            response.status = apiStatusCodes.invalid_input
            response.barcode_list = src.barcodes
            return response
        }
        ServerLogger.log("found product ${products.id} with id ${src.productId}")

        if(src.productDescription != null || src.productDescription != "")
        {
            ServerLogger.log("updating description of product ${products.id} with id ${src.productId}")
            products.description = src.productDescription
        }

        for(barcode in src.barcodes)
        {
            BarCode barCode = new BarCode(code: barcode, products: products)
            if(!barCode.validate())
            {
                ServerLogger.log("Barcode ${barcode} not validated, company trying to register: ${owner.companyId} at ${owner.address}")
                response.barcode_list.add(barcode)
            }
            else
            {
                barCode.save()
                products.addToBarCodes(barCode)
                ServerLogger.log("assigned barcode ${barcode} to product type ${products.id}, registered by ${owner.companyId} at ${owner.address}")
            }
        }

        products.save()
        owner.save()

        if(src.barcodes == null || src.barcodes == [])
        {
            ServerLogger.log("no barcodes supplied, exiting update()")
        }
        else if(response.barcode_list == [])
        {
            ServerLogger.log("report success, exiting update()")
        }
        else
        {
            ServerLogger.log("report failure, exiting update()")
            response.status = apiStatusCodes.invalid_input
        }
        return response
    }

    def delete(BarCodeRegisteringSource src)
    {
        ServerLogger.log("BarCodeService called, method: delete()")
        Map response = [:]
        response.barcode_list = []
        response.status = apiStatusCodes.success

        Company owner = Company.findWhere(token: src.companyToken)
        if(!owner)
        {
            ServerLogger.log("company with token ${src.companyToken} not found, exiting delete()")
            response.status = apiStatusCodes.invalid_token
            response.barcode_list = src.barcodes
            return response
        }

        Products products = Products.get(src.productId)
        if(!products)
        {
            ServerLogger.log("product with id ${src.productId} not found, exiting delete()")
            response.status = apiStatusCodes.invalid_input
            response.barcode_list = src.barcodes
            return response
        }
        if(!owner.hasProduct(products))
        {
            ServerLogger.log("product with id ${src.productId} not found in possession of: ${owner.companyId} at ${owner.address}, exiting delete()")
            response.status = apiStatusCodes.invalid_input
            response.barcode_list = src.barcodes
            return response
        }


        for(barCode in src.barcodes)
        {
            BarCode barcode = BarCode.findWhere(code: barCode, products: products)
            if(!barcode)
            {
                ServerLogger.log("barcode ${barCode} not found")
                response.barcode_list.add(barCode)
                continue
            }
            if(barcode.dateDeleted != null)
            {
                ServerLogger.log("barcode ${barCode} already deleted")
                response.barcode_list.add(barCode)
                continue
            }
            ServerLogger.log("deleting ${barCode}, requested by ${owner.companyId} at ${owner.address}")
            barcode.dateDeleted = new Date()
            barcode.save()
        }

        if(src.barcodes == null || src.barcodes == [])
        {
            ServerLogger.log("no barcodes supplied, exiting delete()")
            response.status = apiStatusCodes.invalid_input
        }
        else if(response.barcode_list == [])
        {
            ServerLogger.log("report success, exiting delete()")
        }
        else
        {
            ServerLogger.log("report failure, exiting delete()")
            response.status = apiStatusCodes.invalid_input
        }
        return response
    }
}
