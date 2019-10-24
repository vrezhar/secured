package com.ttreport.api.response.current

class ProductIdentifier
{
    String uit_code = ""
    String uitu_code = ""
    String description
    long id
    ProductIdentifier(String description, long id)
    {
        this.description = description
        this.id = id
    }
}
