package com.ttreport.data.documents.differentiated.remains

import com.ttreport.data.Company
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.data.products.remains.RemainsProduct

class RemainsDescriptionDocument extends RemainsDocument
{
    @Override
    transient Map getAsMap() {
        Map map = super.getAsMap()
        List products_list = []
        products.each {
            if(it instanceof RemainsProduct){
                products_list.add([
                        "product_gender": it.productGender,
                        "tnved_code_2" : it.tnvedCode2,
                        "release_method": it.releaseMethod

                ])
            }
        }
        map.products_list = products_list
        return map
    }

    static constraints = {
        products nullable: false
    }
}
