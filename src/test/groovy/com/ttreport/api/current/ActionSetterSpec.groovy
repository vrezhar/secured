package com.ttreport.api.current

import com.ttreport.api.resources.current.documents.AcceptanceDocumentCommand
import com.ttreport.api.resources.current.documents.GenericDocumentCommand
import com.ttreport.api.resources.current.products.ProductCommand
import com.ttreport.api.resources.current.documents.ShipmentDocumentCommand
import com.ttreport.auth.Role
import com.ttreport.auth.User
import com.ttreport.auth.UserRole
import com.ttreport.data.products.BarCode
import com.ttreport.data.Company
import com.ttreport.data.products.Products
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class ActionSetterSpec extends Specification implements ServiceUnitTest<ValidationErrorResolverService> {


    List<Class> getDomainClasses()
    {
        [BarCode, Products, Company, User, Role, UserRole]
    }

    void "test action setting: acceptance"()
    {
        AcceptanceDocumentCommand document = new AcceptanceDocumentCommand()
        for(int i = 0; i < 5; ++i){
            document.products.add(new ProductCommand(uit_code: "test", id: 1))
        }
        for(int i = 0; i < 5; ++i){
            document.products.add(new ProductCommand(uit_code: "test"))
        }
        service.setActions(document)
        ArrayList<String> results = new ArrayList<String>(8)
        for(int i = 0; i < 8; i++){
            results.push(document.products[i].action)
        }
        expect:
            results.get(3) == "UPDATE" &&
            results.get(7) == "SAVE"
    }

    void "test action setting: shipment"()
    {
        ShipmentDocumentCommand document = new ShipmentDocumentCommand()
        for(int i = 0; i < 5; ++i){
            document.products.add(new ProductCommand(uit_code: "test", id: 1))
        }
        for(int i = 0; i < 5; ++i){
            document.products.add(new ProductCommand(uit_code: "test"))
        }
        service.setActions(document)
        ArrayList<String> results = new ArrayList<String>(8)
        for(int i = 0; i < 8; i++){
            results.push(document.products[i].action)
        }
        expect:
        results.get(3) == "DELETE" &&
                results.get(7) == "DELETE"
    }
    void "test action setting: invalid document"(){
        GenericDocumentCommand documentCommand = new GenericDocumentCommand()
        String message = null
        try{
            service.setActions(documentCommand)
        }
        catch (Exception e){
            message = e.message
        }
        expect:
            message
    }
}
