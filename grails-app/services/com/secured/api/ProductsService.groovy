package com.secured.api

import com.secured.api.resources.ProductCommand
import com.secured.api.response.Response
import com.secured.data.Products
import grails.gorm.transactions.Transactional

@Transactional
class ProductsService
{

    static scope = 'prototype'

    Products saveOrUpdate(ProductCommand cmd, Response response)
    {
        if(!cmd.validate())
        {
            response.rejectProduct(cmd)
            return null
        }
        Products products = ProductCommand.createOrUpdate(cmd)
        return  products
    }
}
