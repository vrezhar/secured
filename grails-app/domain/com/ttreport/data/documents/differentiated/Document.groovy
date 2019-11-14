package com.ttreport.data.documents.differentiated

import com.ttreport.api.resources.current.DocumentForm
import com.ttreport.data.BarCode
import com.ttreport.data.Company
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Document implements DocumentForm
{
    String requestType
    String documentNumber
    String turnoverType
    long documentDate
    long transferDate

    Date dateCreated
    Date lastUpdated

    static hasMany = [barCodes: BarCode]
    static belongsTo = [company: Company]

    @Override
    Map<String,Object> getAsMap()
    {
        Map<String,Object> map = [
//                document_date: new Date(documentDate).toInstant().toString(),
//                transfer_date: new Date(transferDate).toInstant().toString(),
                document_number: documentNumber,
                document_date: documentDate,
                transfer_date: transferDate,
                request_type: requestType,
                products: barCodes.collect{
                    Map collected = [:]
                    collected.tax = it.products.tax
                    collected.cost = it.products.cost
                    collected.description = it.products.description
                    if(it.uit_code){
                        collected.uit_code = it.uit_code
                    }
                    if(it.uitu_code){
                        collected.uitu_code = it.uitu_code
                    }
                    collected
                }
        ]
        return map
    }


    static constraints = {
        documentNumber nullable: false, blank: false, unique: true
        documentDate nullable: false
        transferDate nullable: false
        turnoverType validator: { String value, Document object ->
            if(value != "SALE" && value != "COMMISSION" && value != "AGENT" && value != "CONTRACT")
                return false
        }
    }
    static mapping = {
        tablePerHierarchy false
    }
}
