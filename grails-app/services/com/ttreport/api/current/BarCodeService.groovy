package com.ttreport.api.current

import com.ttreport.data.BarCode
import grails.gorm.transactions.Transactional

@Transactional
class BarCodeService
{

    boolean isDeleted(BarCode barCode)
    {
        return (barCode.dateDeleted != null)
    }

    BarCode delete(BarCode barCode)
    {
        barCode.dateDeleted = new Date()
        barCode.save()
        return  barCode
    }

    BarCode findNotDeletedByUitCode(String uit){
        return BarCode.where{
            uitCode == uit
            dateDeleted == null
        }.get()
    }
    boolean uitExists(String uit)
    {
        BarCode barCode = BarCode.findWhere(uitCode: uit)
        if(!barCode){
            return false
        }
        if(barCode.dateDeleted){
            return false
        }
        return true
    }
    boolean uituExists(String uitu)
    {
        BarCode barCode = BarCode.findWhere(uituCode: uitu)
        if(!barCode){
            return false
        }
        if(barCode.dateDeleted){
            return false
        }
        return true
    }
}
