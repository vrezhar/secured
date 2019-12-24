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
            for (int i = 0; i < quantity; i++) {
                SerialNumber serialNumber = new SerialNumber(orderUnit: this, serialNumber: SerialNumber.generateRandomSerialNumber())
                serialNumber.save()
            }
            map.serialNumbers = serials
        }
        return map
    }

    static hasMany = [serialNumbers: SerialNumber]
    static belongsTo = [order: Orders]

    static constraints = {
        serialNumbers nullable: true, validator: { List<ProductOrderUnit> value, ProductOrderUnit object ->
            if(object?.serialNumberType == 'SELF_MADE' && !value){
                return false
            }
            if(object?.serialNumberType == 'OPERATOR' && value){
                return false
            }
        }
        quantity min: 1, max: 10
        gtin nullable: false, blank: false
    }
}
