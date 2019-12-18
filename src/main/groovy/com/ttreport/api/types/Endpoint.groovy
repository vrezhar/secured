package com.ttreport.api.types

enum Endpoint
{
    RANDOM_DATA,
    TOKEN,
    ACCEPTANCE,
    SHIPMENT,
    CANCEL_SHIPMENT,
    ENTRANCE,
    RELEASE,
    INDIVIDUAL,
    STATUS,
    FULL_INFO,
    REMAINS_DESCRIPTION,
    FILL_REMAINS_DESCRIPTION,
    REMAINS_REGISTRY,
    CODE_ORDER,
    CODE_ORDER_STATUS,
    CODE_ORDER_FETCH,
    USAGE_REPORT

    Object asType(Class clazz)
    {
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