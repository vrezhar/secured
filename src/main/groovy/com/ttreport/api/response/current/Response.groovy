package com.ttreport.api.response.current


import com.ttreport.api.resources.current.GenericDocumentCommand
import com.ttreport.api.resources.current.ProductCommand
import com.ttreport.configuration.ApplicationConfiguration
import com.ttreport.logs.ServerLogger

class Response extends ApplicationConfiguration
{

    int status = apiStatusCodes.success as int
    private List<RejectedProduct> rejected_list = []
    private List<ProductIdentifier> accepted_list = []

    void accept(ProductCommand cmd)
    {
        ProductIdentifier accepted = new ProductIdentifier(cmd.product_description,cmd.id)
        accepted.uit_code = cmd.uit_code
        accepted.uitu_code = cmd.uitu_code
        accepted_list.add(accepted)
    }

    RejectedProduct rejectProduct(ProductCommand cmd, int reason)
    {
        RejectedProduct rejected = new RejectedProduct()
        rejected.id = cmd.id
        rejected.uit_code = cmd.uit_code
        rejected.uitu_code = cmd.uitu_code
        rejected.reason = reason
        rejected_list?.add(rejected)
        return rejected
    }

    static Map rejectToken(String token_for_logging = "")
    {
        Response response = new Response()
        response.rejectCompanyToken()
        if(token_for_logging) {
            ServerLogger.log("token ${token_for_logging} rejected")
        }
        return response.getAsMap()
    }

    static  Map rejectInput(GenericDocumentCommand cmd, int reason, log = false, String additional_message = null)
    {
        Response response = new Response()
        response.reportInvalidInput()
        for(object in cmd.products) {
            response.rejectProduct(object,reason)
        }
        if(log) {
            ServerLogger.log_validation_errors(cmd,additional_message)
        }
        return response.getAsMap()
    }

    void reportInvalidInput()
    {
        status = apiStatusCodes.invalid_input as int
    }
    void rejectCompanyToken()
    {
        status = apiStatusCodes.invalid_token as int
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
