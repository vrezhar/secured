package com.secured.api.resources

import com.secured.data.BarCode
import com.secured.data.Products
import grails.validation.Validateable

class ProductCommand implements  Validateable
{
    int tax
    int cost
    String product_code
    String product_description
    String uit_code
    String uitu_code
    String action



    static constraints = {
        product_code nullable: false, blank: false
        action validator: {value, object ->
            if(value != "SAVE" && value != "UPDATE")
                return false
        }
        uit_code validator: { value, object ->
            if(object?.action == "SAVE" && (object?.uitu_code == null || object?.uitu_code == "")  && (value == null || value == ""))
                return false
        }
        uitu_code validator: { value, object ->
            if(object?.action == "SAVE" && (object?.uit_code == null || object?.uit_code == "")  && (value == null || value == ""))
                return false
        }
        product_description validator: {value, object ->
            if(object?.action == "SAVE" && (value == null || value == ""))
                return false
        }
        cost validator: {value, object ->
            if(object?.action == "SAVE" && (value == null || value == 0))
                return false
        }
        tax validator: {value, object ->
            if(object?.action == "SAVE" && (value == null || value == 0))
                return false
        }
    }
}
