package com.secured.api.resources

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
    private String action = "DEFAULT"
    void setAction(String action) {this.action = action;}

    static constraints = {
        product_code nullable: false, blank: false
        uit_code validator: { value, object ->
            if((object?.uitu_code == null || object?.uitu_code == "")  && (value == null || value == ""))
                return false
        }
        uitu_code validator: { value, object ->
            if((object?.uit_code == null || object?.uit_code == "")  && (value == null || value == ""))
                return false
        }
        product_description validator: {value, object ->
            if(object?.action == "ACCEPT" && (value == null || value == ""))
                return false
        }
        cost validator: {value, object ->
            if(object?.action == "ACCEPT" && (value == null || value == 0))
                return false
        }
        tax validator: {value, object ->
            if(object?.action == "ACCEPT" && (value == null || value == 0))
                return false
        }
    }
}
