package com.ttreport.api.response.current

class RejectedProduct
{
    String uit_code = ""
    String uitu_code = ""
    String product_code
    private int error_code = 500
    void withReason(int reason)
    {
        this.error_code = (reason < this.error_code) ? reason : this.error_code
    }
}
