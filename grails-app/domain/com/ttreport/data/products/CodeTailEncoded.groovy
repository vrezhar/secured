package com.ttreport.data.products

import com.ttreport.data.products.remains.RemainsBarCode

class CodeTailEncoded
{
    String encodedTail

    static constraints = {
        encodedTail nullable: true, blank: true
    }
}
