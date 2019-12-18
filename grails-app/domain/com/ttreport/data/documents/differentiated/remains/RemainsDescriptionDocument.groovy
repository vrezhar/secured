package com.ttreport.data.documents.differentiated.remains

import com.ttreport.data.Company
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.data.products.remains.RemainsProduct

class RemainsDescriptionDocument extends RemainsDocument
{

    static hasMany = [products: RemainsProduct]

    static constraints = {
    }
}
