package com.ttreport.data.products.remains

class ProductOrderUnit
{

    long productId = 0
    String gtin
    int quantity
    String serialNumberType = 'OPERATOR'

    Date dateCreated
    Date lastUpdated

    transient Map serialize()
    {
        Map<String,Object> map = [
                gtin: gtin,
                quantity: quantity,
                serialNumberType: serialNumberType,
                templateId: 1
        ] as Map<String, Object>
        if(serialNumberType != 'OPERATOR') {
            List<String> serials = []
            serialNumbers.each {
                serials.add(it.serialNumber)
            }
            map.serialNumbers = serials
        }
        return map
    }

    static hasMany = [serialNumbers: SerialNumber]

    static constraints = {
        serialNumbers nullable: true, validator: { List<ProductOrderUnit> value, ProductOrderUnit object ->
            if(object?.serialNumberType == 'SELF_MADE' && !value){
                return false
            }
            if(object?.serialNumberType == 'OPERATOR' && value){
                return false
            }
        }
        quantity min: 1, max: 1500
        gtin nullable: false, blank: false, matches: "\\[0-9]\\{14}"
    }
}
