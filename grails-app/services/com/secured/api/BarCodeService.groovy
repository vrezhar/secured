package com.secured.api

import com.secured.api.resources.BarCodeRegisteringSource
import com.secured.api.response.Responsive
import com.secured.data.BarCode
import com.secured.data.Company
import com.secured.data.Products
import com.secured.data.connectors.CompanyProducts
import com.secured.data.connectors.ProductsBarcodes
import com.secured.logs.DevCycleLogger
import grails.config.Config
import grails.core.support.GrailsConfigurationAware
import grails.gorm.transactions.Transactional

@Transactional
class BarCodeService extends Responsive
{

    def save(BarCodeRegisteringSource src)
    {
        DevCycleLogger.log("BarCodeService called, method: save()")
        Map response = [:]
        if(!src.validate())
        {
            DevCycleLogger.log("barcode command object not validated, exiting save()")
            response.status = statusCodes.invalid_input
            response.not_saved_barcodes_list = src.barcodes ?: []
            return response
        }

        if(src.productCode == null || src.productCode == "")
        {
            DevCycleLogger.log("barcode command object lacks product code, exiting save()")
            response.status = statusCodes.invalid_input
            response.not_saved_barcodes_list = src.barcodes ?: []
            return response
        }
        Company owner = Company.findWhere(token: src.companyToken)
        if(!owner)
        {
            DevCycleLogger.log("company with token ${src.companyToken} not found, exiting save()")
            response.status = statusCodes.invalid_token
            response.not_saved_barcodes_list = src.barcodes
            return response
        }
        Products products = new Products(productCode: src.productCode)
        DevCycleLogger.log("created new product with code ${src.productCode}")
        products.description = src.productDescription ?: ""
        products.save()
        CompanyProducts.create(owner,products)
        DevCycleLogger.log("product saved as property of ${owner.companyId} at ${owner.address}")

        response.product_id = products.id
        response.not_saved_barcodes_list = []
        response.saved_barcodes_list = []

        for(barcode in src.barcodes)
        {
            BarCode barCode = new BarCode(code: barcode, company: owner)
            if(!barCode.validate())
            {
                response.not_saved_barcodes_list.add(barcode)
                DevCycleLogger.log("Barcode ${barcode} not validated")
            }
            else
            {
                barCode.save()
                ProductsBarcodes.create(products,barCode)
                DevCycleLogger.log("Barcode ${barcode} assigned to product type ${products.productCode}")
                response.saved_barcodes_list.add(barcode)
            }
        }
        owner.save()

        if(response.saved_barcode_list != [])
        {
            DevCycleLogger.log("saving changes, report success, exiting save()")
            response.status = statusCodes.success
        }
        else
        {
            DevCycleLogger.log("report failure, exiting save()")
            response.status = statusCodes.invalid_input
        }
        return response

    }

    def update(BarCodeRegisteringSource src)
    {
        DevCycleLogger.log("BarCodeService called, method: update()")
        Map response = [:]

        Company owner = Company.findWhere(token: src.companyToken)
        if(!owner)
        {
            DevCycleLogger.log("company with token ${src.companyToken} not found, exiting update()")
            response.status = statusCodes.invalid_token
            response.not_saved_barcode_list = src.barcodes
            return response
        }
        Products products = Products.get(src.productId)
        if(!products || !owner.hasProducts(products))
        {
            DevCycleLogger.log("product with id ${src.productId} not found in possession of: ${owner.companyId} at ${owner.address}, exiting update()")
            response.status = statusCodes.invalid_input
            response.not_saved_barcode_list = src.barcodes
            return response
        }

        DevCycleLogger.log("found product ${products.productCode} with id ${src.productId}")

        response.not_saved_barcodes_list = []
        response.saved_barcodes_list = []

        for(barcode in src.barcodes)
        {
            BarCode barCode = new BarCode(code: barcode, company: owner)
            if(!barCode.validate())
            {
                DevCycleLogger.log("Barcode ${barcode} not validated, company trying to register: ${owner.companyId} at ${owner.address}")
                response.not_saved_barcodes_list.add(barcode)
            }
            else
            {
                barCode.save()
                ProductsBarcodes.create(products,barCode)
                DevCycleLogger.log("assigned barcode ${barcode} to product type ${products.productCode}, registered by ${owner.companyId} at ${owner.address}")
                response.saved_barcodes_list.add(barcode)
            }
        }
        owner.save()

        if(response.saved_barcodes_list != [])
        {
            DevCycleLogger.log("saving changes, report success, exiting update()")
            response.status = statusCodes.success
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
        Company owner = Company.findWhere(token: src.companyToken)
        if(!owner)
        {
            DevCycleLogger.log("company with token ${src.companyToken} not found, exiting delete()")
            response.status = statusCodes.invalid_token
            response.not_deleted_barcode_list = src.barcodes
            return response
        }

        Products products = Products.get(src.productId)
        if(!products)
        {
            DevCycleLogger.log("product with id ${src.productId} not found, exiting delete()")
            response.status = statusCodes.invalid_input
            response.not_deleted_barcode_list = src.barcodes
            return response
        }
        if(!owner.hasProducts(products))
        {
            DevCycleLogger.log("product with id ${src.productId} not found in possession of: ${owner.companyId} at ${owner.address}, exiting delete()")
            response.status = statusCodes.invalid_input
            response.not_deleted_barcode_list = src.barcodes
            return response
        }

        response.not_deleted_barcode_list = []
        response.deleted_barcode_list = []

        for(barCode in src.barcodes)
        {
            BarCode barcode = BarCode.findWhere(code: barCode, company: owner)
            if(barcode.dateDeleted != null)
            {
                DevCycleLogger.log("barcode ${barCode} already deleted")
                response.deleted_barcode_list.add(barCode)
                continue
            }
            if(owner.getBarCodesOf(products).contains(barcode))
            {
                DevCycleLogger.log("deleting ${barCode}, requested by ${owner.companyId} at ${owner.address}")
                barcode.dateDeleted = new Date()
                response.deleted_barcode_list.add(barCode)
                barcode.save()
                continue
            }
            DevCycleLogger.log("request of ${owner.companyId} at ${owner.address} to delete ${barcode} denied")
            response.not_deleted_barcode_list.add(barCode)
        }

        if(response.deleted_barcode_list != [])
        {
            DevCycleLogger.log("saving changes, report success, exiting delete()")
            response.status = statusCodes.success
        }
        else
        {
            DevCycleLogger.log("report failure, exiting delete()")
            response.status = statusCodes.invalid_input
        }
        return response
    }
}
