package com.ttreport.data.documents.differentiated.remains

import com.ttreport.data.Company
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.data.products.remains.FullRemainsProduct

class RemainsFullDescriptionDocument extends RemainsDocument
{
    static hasMany = [products: FullRemainsProduct]

    static constraints = {

    }
}
