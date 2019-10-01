package com.secured.api

import com.secured.api.resources.ProductCommand
import com.secured.data.BarCode
import com.secured.data.Company
import com.secured.data.Products
import grails.gorm.transactions.Transactional

@Transactional
class ProductsService
{
    static scope = 'prototype'

    Products update(ProductCommand cmd, Company company, boolean ships = false)
    {
        cmd.setAction("UPDATE")
        if(!cmd.validate())
            return null
        Products products = Products.findWhere(productCode: cmd.product_code, company: company)
        if(products)
        {
            if(ships)
            {
                BarCode barCode = BarCode.findWhere(uit_code: cmd.uit_code, uitu_code: cmd.uitu_code, products: products)
                if(!barCode)
                    return null
                barCode.dateDeleted = new Date()
                barCode.save()
                products.save()
                return products
            }
            products.description = (cmd.product_code != null && cmd.product_code != "") ? cmd.product_code : products.description
            if(!products.validate())
                return  null
            if((cmd.uitu_code != null && cmd.uitu_code != "") || (cmd.uit_code != null && cmd.uit_code != ""))
            {
                BarCode barCode = new BarCode(uit_code: cmd.uit_code, uitu_code: cmd.uitu_code, products: products)
                if(!barCode.validate())
                    return null
                products.addToBarCodes(barCode)
            }
            products.save()
            return products
        }
        return null
    }

    Products save(ProductCommand cmd, Company company)
    {
        cmd.setAction("SAVE")
        if(!cmd.validate())
            return null
        Products products = new Products(productCode: cmd.product_code, description: cmd.product_description, tax: cmd.tax, cost: cmd.cost, company: company)
        if(!products.validate())
            return null
        BarCode barCode = new BarCode(uit_code: cmd.uit_code, uitu_code: cmd.uitu_code, products: products)
        if(!barCode.validate())
            return null
        products.addToBarCodes(barCode)
        products.save()
        return products
    }

    boolean exists(String productCode, Company company)
    {
        Products products = Products.findWhere(productCode: productCode, company: company)
        if(products)
            return true
        return false
    }

}
