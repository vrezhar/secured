package com.secured.api

import com.secured.api.resources.ProductCommand
import grails.gorm.transactions.Transactional

@Transactional
class ProductsService
{

    static scope = 'prototype'

    def save(ProductCommand cmd)
    {

    }

    def update(ProductCommand cmd)
    {

    }
}
