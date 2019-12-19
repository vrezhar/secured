package com.ttreport.api.resources.current.remains

import com.ttreport.data.products.BarCode
import com.ttreport.data.products.Products
import grails.validation.Validateable

class ProductRemainsDescriptionCommand implements Validateable
{
    String description_type = 'EXTENDED'
    String identifier

    String description
    String tax
    String cost

    String product_gender
    String tnved_code_2
    String release_method

    String model
    String product_name
    String brand
    String country
    String product_type
    String material_upper
    String material_lining
    String material_down
    String color
    String product_size
    String certificate_type
    String certificate_date
    String certificate_number


    static constraints = {

        description nullable: true, blank: true
        tax nullable: true, blank: true
        cost nullable: true, blank: true

        identifier nullable: true, validator: { String value, ProductRemainsDescriptionCommand object ->
            if(object?.description_type == "SIMPLE" && !value && !object?.description){
                return false
            }
        }
        product_gender nullable: true, validator: { String value, ProductRemainsDescriptionCommand object ->
            if(object?.description_type == "SIMPLE" && !value){
                return false
            }
        }
        tnved_code_2 nullable: true, validator: { String value, ProductRemainsDescriptionCommand object ->
            if(object?.description_type == "SIMPLE" && !value){
                return false
            }
        }
        release_method nullable: true, validator: { String value, ProductRemainsDescriptionCommand object ->
            if(object?.description_type == "SIMPLE" && !value){
                return false
            }
        }

        model nullable: true, validator: { String value, ProductRemainsDescriptionCommand object ->
            if(object?.description_type != "SIMPLE" && !value){
                return false
            }
        }
        product_name nullable: true, validator: { String value, ProductRemainsDescriptionCommand object ->
            if(object?.description_type != "SIMPLE" && !value && !object?.description){
                return false
            }
        }
        brand nullable: true, validator: { String value, ProductRemainsDescriptionCommand object ->
            if(object?.description_type != "SIMPLE" && !value){
                return false
            }
        }
        country nullable: true, blank: true
        product_type nullable: true, validator: { String value, ProductRemainsDescriptionCommand object ->
            if(object?.description_type != "SIMPLE" && !value){
                return false
            }
        }
        material_upper nullable: true, validator: { String value, ProductRemainsDescriptionCommand object ->
            if(object?.description_type != "SIMPLE" && !value){
                return false
            }
        }
        material_lining nullable: true, validator: { String value, ProductRemainsDescriptionCommand object ->
            if(object?.description_type != "SIMPLE" && !value){
                return false
            }
        }
        material_down nullable: true, validator: { String value, ProductRemainsDescriptionCommand object ->
            if(object?.description_type != "SIMPLE" && !value){
                return false
            }
        }
        color nullable: true, blank: true
        product_size nullable: true, blank: true
        certificate_type  nullable: true, validator: { String value, ProductRemainsDescriptionCommand object ->
            if(object?.description_type != "SIMPLE" && !value){
                return false
            }
        }
        certificate_date  nullable: true, validator: { String value, ProductRemainsDescriptionCommand object ->
            if(object?.description_type != "SIMPLE" && !value){
                return false
            }
        }
        certificate_number  nullable: true, validator: { String value, ProductRemainsDescriptionCommand object ->
            if(object?.description_type != "SIMPLE" && !value){
                return false
            }
        }

    }
}
