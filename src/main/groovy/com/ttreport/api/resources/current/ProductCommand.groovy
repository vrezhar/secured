package com.ttreport.api.resources.current

import com.ttreport.data.BarCode
import com.ttreport.data.Products
import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class ProductCommand implements Validateable
{
    boolean rejected = false
    int tax
    int cost
    long id
    String product_description
    String uit_code
    String uitu_code
    String action

    private static boolean isExtended(Map rawJson)
    {
        return rawJson?.tnved_code || rawJson?.certificate_document ||
                rawJson?.certificate_document_number || rawJson?.certificate_document_date ||
                rawJson?.producer_inn || rawJson?.owner_inn || rawJson?.production_date
    }

    static ProductCommand bind(Map rawJson)
    {
        ProductCommand command = new ProductCommand()
        command.uit_code = rawJson?.uit_code
        command.uitu_code = rawJson?.uitu_code
        command.tax = (rawJson?.tax == null) ? 0 : rawJson?.tax as Integer
        command.cost = (rawJson?.cost == null) ? 0 : rawJson?.cost as Integer
        command.id = (rawJson?.id == null) ? 0L : rawJson?.id as Long
        command.product_description = rawJson?.product_description
        if(!rawJson){
            return command
        }
        ExtendedProductCommand productCommand = ExtendedProductCommand.createFromBase(command)
        productCommand.tnved_code = rawJson?.tnved_code
        productCommand.certificate_document = rawJson?.certificate_document
        productCommand.certificate_document_number = rawJson?.certificate_document_number
        productCommand.certificate_document_date = rawJson?.certificate_document_date
        productCommand.producer_inn = rawJson?.producer_inn
        productCommand.owner_inn = rawJson?.owner_inn
        productCommand.production_date = rawJson?.production_date
        return productCommand
    }

    static constraints = {
        id nullable: true, validator: { long value, ProductCommand object ->
            Products databaseInstance = Products.get(object?.id)
            if (!value && object?.action != "SAVE") {
                return 'command.product.null'
            }
            if (!databaseInstance && object?.action != "SAVE") {
                return 'command.product.notfound'
            }
        }
        action validator: { String value, ProductCommand object ->
            if (value != "SAVE" && value != "UPDATE" && value != "DELETE") {
                return false
            }
        }

        uit_code nullable: true, validator: { String value, ProductCommand object ->
            BarCode exists = BarCode.findWhere(uitCode: value ?: null, uituCode: object?.uitu_code ?: null)
            if (object?.action == "SAVE" && !(value || object?.uitu_code)) {
                return 'command.code.uit.null'
            }
            if (!exists && object?.action == "DELETE") {
                return 'command.code.notfound'
            }
            if (exists && object?.action != "DELETE") {
                return 'command.code.duplicate'
            }
            if (exists && exists?.dateDeleted) {
                return 'command.code.deleted'
            }
            if (exists && object?.action != "SAVE") {
                Products products = Products.get(object?.id)
                if (!products?.hasBarcode(exists)) {
                    return 'command.code.notfound'
                }

            }
        }
            uitu_code nullable: true, validator: { String value, ProductCommand object ->
                BarCode exists = BarCode.findWhere(uitCode: object?.uit_code?: null, uituCode: value ?: null)
                if (object?.action == "SAVE" && !object?.uit_code && !value) {
                    return 'command.code.uitu.null'
                }
                if (!exists && object?.action == "DELETE") {
                    return 'command.code.notfound'
                }
                if (exists && object?.action != "DELETE") {
                    return 'command.code.duplicate'
                }
                if (exists && exists?.dateDeleted) {
                    return 'command.code.deleted'
                }
                if (exists && object?.action != "SAVE") {
                    Products products = Products.get(object?.id)
                    if (!products?.hasBarcode(exists)) {
                        return 'command.code.notfound'
                    }
                }

            }
            product_description nullable: true, validator: { String value, ProductCommand object ->
                if (object?.action == "SAVE" && !value) {
                    return 'command.product.description.null'
                }
            }
            cost validator: { int value, ProductCommand object ->
                if (object?.action == "SAVE" && (value == 0)) {
                    return 'command.product.cost.null'
                }
            }
            tax validator: { int value, ProductCommand object ->
                if (object?.action == "SAVE" && (value == 0)) {
                    return 'command.product.tax.null'
                }
            }
        }
    }
