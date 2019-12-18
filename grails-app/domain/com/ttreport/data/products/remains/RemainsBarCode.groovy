package com.ttreport.data.products.remains

import com.ttreport.data.products.BarCode
import com.ttreport.data.products.CodeTailEncoded


class RemainsBarCode extends BarCode
{

    static hasOne = [cryptoTail: CodeTailEncoded]

    static constraints = {
    }
}
