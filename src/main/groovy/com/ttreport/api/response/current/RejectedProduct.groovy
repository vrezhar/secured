package com.ttreport.api.response.current

class RejectedProduct
{
    String uit_code = ""
    String uitu_code = ""
    String product_code
    int reason
    void withReason(int reason)
    {
        this.reason = reason
    }
}
