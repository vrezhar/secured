package com.ttreport.api.resources.current

import com.ttreport.data.Company
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.logs.DevCycleLogger
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class DocumentCommand implements Validateable
{
    List<ProductCommand> products = []
    String document_number
    String document_date = new Date().toInstant().toString()
    String companyToken

    static DocumentCommand bind(Map rawJson, String bindTo)
    {
        println(rawJson)
        DocumentCommand cmd = new DocumentCommand()
        try{
            (rawJson?.products as List<Map>).each {
                cmd.products.add(ProductCommand.bind(it,bindTo))
            }
            cmd.companyToken = rawJson?.companyToken
            cmd.document_number = rawJson?.document_number
            cmd.document_date = rawJson?.document_date
            if(bindTo == "MARKET_ENTRANCE"){
                MarketEntranceCommand command = MarketEntranceCommand.createFromBase(cmd)
                command.production_date = rawJson?.production_date
                if(rawJson?.producer_inn){
                    command.producer_inn = rawJson?.producer_inn
                }
                if(rawJson?.owner_inn){
                    command.owner_inn = rawJson?.owner_inn
                }
                command.production_type = rawJson?.production_type
                command.doc_type = rawJson?.doc_type
                return command
            }
            if(bindTo == "RELEASE"){
                ReleaseCommand command = ReleaseCommand.createFromBase(cmd)
                command.products.each {

                }
                command.order_date = rawJson?.order_date
                command.order_number = rawJson?.order_number
                command.action = rawJson?.action as int
                command.action_date = rawJson?.action_date
                command.document_type = rawJson?.document_type == null ? 0 : rawJson?.document_type as int
                return  command
            }
        }
        catch (Exception e){
            DevCycleLogger.log(e.message)
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
        document_number nullable: true, validator: { String value, DocumentCommand object ->
            if( !(object instanceof MarketEntranceCommand || object instanceof FromPhysCommand) && (!value || Document.findByDocumentNumber(value)) ){
                return false
            }
        }
    }
}
