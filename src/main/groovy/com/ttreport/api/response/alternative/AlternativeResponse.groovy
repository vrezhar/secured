package com.ttreport.api.response.alternative

import com.ttreport.api.resources.alternative.AlternativeProductCommand
import com.ttreport.api.response.current.RejectedProduct
import com.ttreport.api.response.Responsive
import com.ttreport.data.Products

class AlternativeResponse extends Responsive
{
    private int status = statusCodes.success as int
    private long product_id = -1
    private List<RejectedProduct> rejected_list = []

    void rejectProducts(AlternativeProductCommand cmd)
    {
        RejectedProduct rejected = new RejectedProduct(product_code: cmd.product_code?: "")
        for(codes in cmd.codes)
        {
            rejected.uit_code = codes.uit_code
            rejected.uitu_code = codes.uitu_code
            rejected_list?.add(rejected)
        }
    }

    void setId(long id)
    {
        product_id = id >= 0 ? id : -1
    }

    void setIdByCode(String product_code)
    {
        product_id = Products.findWhere(productCode: product_code)?.id ?: -1
    }
    void reportInvalidInput()
    {
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
        if(product_id != -1)
            response.product_id = -1
        if(rejected_list)
            response.rejected_list = rejected_list
        return response
    }
}
