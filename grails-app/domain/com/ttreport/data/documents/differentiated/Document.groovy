package com.ttreport.data.documents.differentiated

import com.ttreport.api.resources.current.DocumentForm
import com.ttreport.data.BarCode
import com.ttreport.data.Company
import com.ttreport.data.MarketEntranceBarCode
import com.ttreport.data.documents.differentiated.existing.MarketEntranceDocument
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Document implements DocumentForm, Serializable
{
    private static final long serialVersionUID = 2L

    String documentId
    String documentStatus

    Date dateCreated
    Date lastUpdated

    static hasMany = [barCodes: BarCode]
    static belongsTo = [company: Company]

    @Override
    transient Map<String,Object> getAsMap()
    {
        Map<String,Object> map = [:]
        if(this instanceof MarketEntranceDocument){
            map.products = barCodes.collect{
                Map collected = [:]
                if(it.uitCode){
                    collected.uit_code = it.uitCode
                }
                if(it.uituCode){
                    collected.uitu_code = it.uituCode
                }
                if(it instanceof MarketEntranceBarCode) {
                    MarketEntranceBarCode code = it as MarketEntranceBarCode
                    collected.tnved_code = code.tnvedCode
                    collected.certificate_document = code.certificateDocument
                    collected.certificate_document_date = code.certificateDocumentDate
                    collected.certificate_document_number = code.certificateDocumentNumber
                    if(code.productionDate) {
                        collected.production_date = code.productionDate
                    }
                    if(code.producerInn){
                        collected.producer_inn = code.producerInn
                    }
                    if(code.ownerInn){
                        collected.owner_inn = code.ownerInn
                    }
                }
                collected
            }
            return map
        }
        map.products = barCodes?.collect{
            Map collected = [:]
            collected.product_tax = it?.products?.tax
            collected.product_cost = it?.products?.cost
            if(it.minified){
                collected.cis = it.uitCode?: it.uituCode //more likely only uit should apply
                return collected
            }
            collected.product_description = it?.products?.description
            if(it.uitCode){
                collected.uit_code = it.uitCode
            }
            if(it.uituCode){
                collected.uitu_code = it.uituCode
            }
            collected
        }
        return map
    }

    static constraints = {
        documentId nullable: true, blank: true
        documentStatus nullable: true, blank: true
        company nullable: false
        barCodes nullable: false
    }
    static mapping = {
        tablePerHierarchy false
    }
}
