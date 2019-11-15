package com.ttreport.api.resources.current

import com.ttreport.data.Company
import com.ttreport.data.documents.differentiated.Document
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class DocumentCommand implements Validateable
{
    List<ProductCommand> products = []
    String document_number
    String document_date = new Date().toInstant().toString()
    String companyToken

    private static boolean isCirculation(Map rawJson)
    {
        return rawJson?.production_date || rawJson?.producer_inn || rawJson?.owner_inn ||
                rawJson?.production_type || rawJson?.doc_type
    }

    private static boolean isRelease(Map rawJson)
    {
        return rawJson?.order_number || rawJson?.order_date || rawJson?.action ||
                rawJson?.action_date || rawJson?.document_type
    }

    static DocumentCommand bind(Map rawJson)
    {
        DocumentCommand cmd = new DocumentCommand()
        (rawJson?.products as List<Map>).each {
            cmd.products.add(ProductCommand.bind(it))
        }
        cmd.companyToken = rawJson?.companyToken
        cmd.document_number = rawJson?.document_number
        cmd.document_date = rawJson?.document_date
        if(isCirculation(rawJson)){
            MarketEntranceCommand command = MarketEntranceCommand.createFromBase(cmd)
            command.production_date = rawJson?.production_date
            command.producer_inn = rawJson?.producer_inn
            command.owner_inn = rawJson?.owner_inn
            command.production_type = rawJson?.production_type
            command.doc_type = rawJson?.doc_type
            return command
        }
        if(isRelease(rawJson)){
            ReleaseCommand command = ReleaseCommand.createFromBase(cmd)
            command.order_date = rawJson?.order_date
            command.order_number = rawJson?.order_number
            command.action = rawJson?.action
            command.action_date = rawJson?.action_date
            command.document_type = rawJson?.document_type as int
            return  command
        }
        return cmd
    }

    static constraints = {
        document_date nullable: false, blank: false
        products nullable: false, validator: { List<ProductCommand> value, DocumentCommand object ->
            if(value?.isEmpty()) {
                return false
            }
        }
        companyToken nullable: false, blank: false, validator: { String value, DocumentCommand object ->
            Company company = Company.findWhere(token: value)
            if(!company){
                return false
            }
        }
        document_number validator: { String value, DocumentCommand object ->
            if(Document.findByDocumentNumber(value)){
                return false
            }
        }
    }
}
