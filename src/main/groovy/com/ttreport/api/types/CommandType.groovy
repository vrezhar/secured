package com.ttreport.api.types

enum CommandType
{
    ENTRANCE ,
    RELEASE ,
    ACCEPTANCE ,
    SHIPMENT ,
    INDIVIDUAL ,
    CANCEL_SHIPMENT,
    PRODUCT,
    EXTENDED_PRODUCT

    Object asType(Class clazz)
    {
        if(clazz == ProductType.class){
            if(this == PRODUCT){
                return ProductType.SIMPLE
            }
            if(this == EXTENDED_PRODUCT){
                return ProductType.EXTENDED
            }
        }
        if(clazz == DocumentType.class){
            switch (this){
                case ACCEPTANCE:
                    return DocumentType.ACCEPTANCE
                case SHIPMENT:
                    return DocumentType.SHIPMENT
                case CANCEL_SHIPMENT:
                    return DocumentType.CANCEL_SHIPMENT
                case ENTRANCE:
                    return DocumentType.ENTRANCE
                case RELEASE:
                    return DocumentType.RELEASE
                case INDIVIDUAL:
                    return DocumentType.INDIVIDUAL
                default:
                    return super.asType(clazz)
            }
        }
        return super.asType(clazz)
    }
}