package com.ttreport.data.products.remains

import com.ttreport.data.Company
import com.ttreport.data.products.Products

class RemainsProduct extends Products
{

    String productGender
    String tnvedCode2
    String releaseMethod

    static constraints = {
        productGender nullable: false, blank: false
        tnvedCode2 nullable: false, blank: false
        releaseMethod nullable: false, blank: false
    }
}
