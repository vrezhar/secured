package com.secured.api

import com.secured.api.resources.BarCodeRegisteringSource
import com.secured.data.BarCode
import com.secured.data.Company
import grails.gorm.transactions.Transactional

@Transactional
class BarCodeService {

    def registerBarCodes(BarCodeRegisteringSource src)
    {
        Company owner = Company.findWhere(token: src.companyToken)
        if(!owner)
            return false
        //validate barcodes before proceeding
        for(barcode in src.barcodeList)
        {
            //barcodes can be validated here too
            BarCode barCode = new BarCode(code: barcode, company: owner)
            barCode.save()
            owner.addToBarCodes(barCode)
        }
        owner.save()
    }
}
