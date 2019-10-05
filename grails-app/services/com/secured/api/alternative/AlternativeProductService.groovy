package com.secured.api.alternative

import com.secured.api.resources.alternative.AlternativeDocumentCommand
import com.secured.api.resources.alternative.AlternativeProductCommand
import com.secured.api.response.alternative.AlternativeResponse
import com.secured.data.Company
import com.secured.data.Products
import com.secured.logs.DevCycleLogger
import grails.gorm.transactions.Transactional

@Transactional
class AlternativeProductService
{

    def save(AlternativeDocumentCommand cmd)
    {
        DevCycleLogger.log("save() called")
        Company company = Company.findByToken(cmd.companyToken)
        if(!company)
        {
            DevCycleLogger.log("company with token ${cmd.companyToken} not found, reporting authorization failure, exiting accept()")
            AlternativeResponse response = new AlternativeResponse()
            response.rejectCompanyToken()
            return response.getAsMap()
        }
        if(!cmd.validate())
        {
            cmd.errors.fieldErrors.each {
                DevCycleLogger.log("couldn't validate value ${it.rejectedValue} for field ${it.field}")
            }
            DevCycleLogger.log("command object not validated, nothing saved, exiting save()")
            return null
        }
        List<Products> productsList = []
        for(product in cmd.products)
        {
            if(!product.validate())
            {
                cmd.errors.fieldErrors.each {
                    DevCycleLogger.log("couldn't validate value ${it.rejectedValue} for field ${it.field}")
                }
                DevCycleLogger.log("internal structure error, rejecting")
                product.rejected = true
                continue
            }
            if(product.product_id != -1)
            {
                DevCycleLogger.log("valid id detected, executing update()")
                Products updated = update(product)
                if(updated)
                {
                    productsList.add(updated)
                    DevCycleLogger.log("product with id ${product.product_id} updated")
                }
                continue
            }
            Products products = Products.findWhere(productCode: product.product_code)
            if(products)
            {
                DevCycleLogger.log("valid code detected, executing update()")
                Products updated = update(product)
                if(updated)
                {
                    productsList.add(updated)
                    DevCycleLogger.log("product with id ${product.product_id} updated")
                }
                continue
            }
            DevCycleLogger.log("no product found in the database, trying to save")
            products = new Products(productCode: product.product_code)
            if(product.tax == -1 || product.cost == -1 || product.product_description == null || product.product_description == "")
            {
                product.rejected = true
                DevCycleLogger.log("description, tax or cost missing, not saving the current product")
                continue
            }
            products.tax = product.tax
            products.cost = product.cost

        }
    }

    Products update(AlternativeProductCommand cmd)
    {

    }

    def delete(AlternativeDocumentCommand cmd)
    {

    }
}
