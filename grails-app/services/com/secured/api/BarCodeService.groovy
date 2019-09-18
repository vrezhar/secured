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
            return ["INVALID_TOKEN"]
        //validate barcodes before proceeding, presumably with src.validate()
        for(barcode in src.barcodes)
        {
            BarCode barCode = new BarCode(code: barcode, company: owner)
            //individual barcodes should be validated too
            barCode.save()
            owner.addToBarCodes(barCode)
        }
        owner.save()
        return src.barcodes
    }

    def delete(BarCodeRegisteringSource src)
    {
        Company owner = Company.findWhere(token: src.companyToken)
        if(!owner)
            return ["INVALID_TOKEN"]
        def list_not_deleted = ["INVALID_INPUT"]
        for(barCode in src.barcodes)
        {
            //validate barcodes before deleting
            BarCode barcode = contains(owner,barCode)
            if(barcode)
            {
                barcode.dateDeleted = new Date()
                barcode.save()
                continue
            }
            list_not_deleted.add(barCode)
        }
        if(list_not_deleted[1] != null)
            return list_not_deleted
        return src.barcodes
    }

    protected static BarCode contains(Company company,String code)
    {
        for(barcode in company.barCodes)
        {
            if(barcode.code == code)
                return barcode
        }
        return null
    }

}
