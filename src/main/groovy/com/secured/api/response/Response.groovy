package com.secured.api.response

import com.secured.api.resources.ProductCommand
import com.secured.data.BarCode
import com.secured.data.Products

class Response extends  Responsive
{
    protected class RejectedProduct
    {
        String uit_code = ""
        String uitu_code = ""
        String productCode
    }
    private int status = statusCodes.success as int
    private List<RejectedProduct> rejected_list = null

    void rejectProduct(ProductCommand cmd)
    {
        RejectedProduct rejected = new RejectedProduct(productCode: cmd.product_code)
        if(cmd.uit_code)
            rejected.uit_code = cmd.uit_code
        if(cmd.uitu_code)
            rejected.uitu_code = cmd.uitu_code
        rejected_list.add(rejected)
        status = statusCodes.invalid_input as int
    }

    void rejectCompanyToken()
    {
        status = statusCodes.invalid_token as int
    }

    Map getAsMap()
    {
        Map response = [:]
        response.status = this.status
        if(rejected_list)
            response.rejected_list = rejected_list
        return response
    }
}
