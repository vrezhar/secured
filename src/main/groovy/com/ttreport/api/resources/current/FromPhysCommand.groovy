package com.ttreport.api.resources.current

class FromPhysCommand extends DocumentCommand
{
    String participant_inn
    List<ProductCommand> getProducts_list()
    {
        return products
    }
    void setProducts_list(List<ProductCommand> products)
    {
        this.products = products
    }
    static constraints = {
        importFrom DocumentCommand
        participant_inn nullable: true, blank: true
    }
}
