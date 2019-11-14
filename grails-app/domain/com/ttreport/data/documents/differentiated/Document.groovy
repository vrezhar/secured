package com.ttreport.data.documents.differentiated

import com.ttreport.api.resources.current.DocumentForm
import com.ttreport.data.BarCode
import com.ttreport.data.CirculationBarCode
import com.ttreport.data.Company
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Document implements DocumentForm
{

    String documentDate
    String documentNumber

    Date dateCreated
    Date lastUpdated

    static hasMany = [barCodes: BarCode]
    static belongsTo = [company: Company]

    @Override
    Map<String,Object> getAsMap()
    {
        Map<String,Object> map =
                [
                        document_number: documentNumber,
                        document_date: documentDate,
                        products: barCodes.collect{
                            Map collected = [:]
                            if(it.minified){
                                collected.cis = it.uitCode?: it.uituCode
                                collected.tax = it.products.tax
                                collected.cost = it.products.cost
                                return collected
                            }
                            collected.tax = it.products.tax
                            collected.cost = it.products.cost
                            collected.description = it.products.description
                            if(it.uitCode){
                                collected.uit_code = it.uitCode
                            }
                            if(it.uituCode){
                                collected.uitu_code = it.uituCode
                            }
                            if(it instanceof CirculationBarCode)
                            {
                                CirculationBarCode code = it as CirculationBarCode
                                collected.tnved_code = code.tnvedCode
                                collected.certificate_document = code.certificateDocument
                                collected.certificate_document_date = code.certificateDocumentDate
                                collected.certificate_document_number = code.certificateDocumentNumber
                                collected.production_date = code.productionDate
                                collected.producer_inn = code.producerInn
                                collected.owner_inn = code.ownerInn
                            }
                            collected
                        }
                ]
        return map
    }

    static constraints = {
    }
    static mapping = {
        tablePerHierarchy false
    }
}
