package com.ttreport.data.documents.differentiated.remains

import com.ttreport.data.Company
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.data.products.remains.FullRemainsProduct

class RemainsFullDescriptionDocument extends RemainsDocument
{
    @Override
    transient Map getAsMap()
    {
        Map map = super.getAsMap()
        List products_list = []
        products.each {
            if(it instanceof FullRemainsProduct){
                products_list.add(["model": it.model,
                                   "product_name" : it.productName,
                                   "brand": it.brand,
                                   "country": it.country,
                                   "product_type" : it.productType,
                                   "material_upper": it.materialUpper,
                                   "material_lining": it.materialLining,
                                   "material_down" : it.materialDown,
                                   "color": it.color,
                                   "product_size": it.productSize,
                                   "certificate_type" : it.certificateType,
                                   "certificate_date" : it.certificateDate,
                                   "certificate_number": it.certificateNumber
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
