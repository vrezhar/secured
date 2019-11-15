package com.ttreport.data.documents.differentiated

import com.ttreport.api.resources.current.DocumentForm
import com.ttreport.data.BarCode
import com.ttreport.data.CirculationBarCode
import com.ttreport.data.Company
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Document implements DocumentForm,Serializable
{

    private static long document_counter = 0L
    private static final long serialVersionUID = 1

    String documentDate = new Date().toInstant().toString()
    String documentNumber = { ->
        while(findWhere(documentNumber: documentNumber.toString())){
            ++document_counter
        }
        document_counter.toString()
    }

    Date dateCreated
    Date lastUpdated

    static hasMany = [barCodes: BarCode]
    static belongsTo = [company: Company]

    @Override
    transient Map<String,Object> getAsMap()
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
                            if(it instanceof CirculationBarCode) {
                                CirculationBarCode code = it as CirculationBarCode
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
                ]
        return map
    }

    static constraints = {
        documentNumber nullable: false, blank: false, unique: true
        company nullable: false
        barCodes nullable: false
    }
    static mapping = {
        tablePerHierarchy false
    }
}
