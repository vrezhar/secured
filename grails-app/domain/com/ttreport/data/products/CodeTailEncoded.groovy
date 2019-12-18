package com.ttreport.data.products

import com.ttreport.data.products.remains.RemainsBarCode

class CodeTailEncoded
{
    String encodedTail

    static belongsTo = [barCode: RemainsBarCode]

    static constraints = {
    }
}
