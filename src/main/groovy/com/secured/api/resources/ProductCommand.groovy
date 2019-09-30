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
    private String action = "DEFAULT"
    void setAction(String action) {this.action = action;}

    static Products createOrUpdate(ProductCommand cmd)
    {
        Products products = Products.findWhere(productCode: cmd.product_code)
        if(products)
        {
            products.description = (cmd.product_code != null && cmd.product_code != "") ? cmd.product_code : products.description
            products.addToBarCodes(new BarCode(uit_code: cmd.uit_code, uitu_code: cmd.uitu_code, products: products))
            products.save()
            return products
        }
        products = new Products(productCode: cmd.product_code, description: cmd.product_description, tax: cmd.tax, cost: cmd.cost)
        products.addToBarCodes(new BarCode(uit_code: cmd.uit_code, uitu_code: cmd.uitu_code, products: products))
        return products
    }

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
