package com.ttreport.data.documents.differentiated.remains

import com.ttreport.data.Company
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.data.products.remains.RemainsBarCode

class RemainsRegistryDocument extends Document
{
    static constraints = {
        importFrom Document
    }
}
