package com.ttreport.api.response.current

import com.ttreport.api.resources.current.DocumentCommand
import com.ttreport.api.resources.current.ProductCommand
import com.ttreport.api.response.Responsive
import com.ttreport.data.Products
import com.ttreport.logs.DevCycleLogger

class Response extends  Responsive
{

    private int status = statusCodes.success as int
    private List<RejectedProduct> rejected_list = []
    private List<ProductIdentifier> accepted_list = []

    void accept(Products products)
    {
        accepted_list.add(new ProductIdentifier(products.description,products.id))
    }

    RejectedProduct rejectProduct(ProductCommand cmd)
    {
        RejectedProduct rejected = new RejectedProduct()
        rejected.uit_code = cmd.uit_code
        rejected.uitu_code = cmd.uitu_code
        rejected_list?.add(rejected)
        return rejected
    }

    static Map rejectToken(String token_for_logging = "")
    {
        Response response = new Response()
        response.rejectCompanyToken()
        if(token_for_logging) {
            DevCycleLogger.log("token ${token_for_logging} rejected")
        }
        return response.getAsMap()
    }

    static  Map rejectInput(DocumentCommand cmd, log = false, String additional_message = null)
    {
        Response response = new Response()
        response.reportInvalidInput()
        for(object in cmd.products) {
            response.rejectProduct(object)
        }
        if(log) {
            DevCycleLogger.log_validation_errors(cmd,additional_message)
        }
        return response.getAsMap()
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
        if(rejected_list){
            response.rejected_list = rejected_list
        }
        if(accepted_list){
            response.accepted_list = accepted_list
        }
        return response
    }
}
