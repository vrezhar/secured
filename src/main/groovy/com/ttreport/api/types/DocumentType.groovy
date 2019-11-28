package com.ttreport.api.types

enum DocumentType
{
    ENTRANCE ,
    RELEASE ,
    ACCEPTANCE ,
    SHIPMENT ,
    INDIVIDUAL ,
    CANCEL_SHIPMENT

    Object asType(Class clazz)
    {
        if(clazz == Endpoint.class){
            switch (this){
                case ACCEPTANCE:
                    return Endpoint.ACCEPTANCE
                case SHIPMENT:
                    return Endpoint.SHIPMENT
                case CANCEL_SHIPMENT:
                    return Endpoint.CANCEL_SHIPMENT
                case ENTRANCE:
                    return Endpoint.ENTRANCE
                case RELEASE:
                    return Endpoint.RELEASE
                case INDIVIDUAL:
                    return Endpoint.INDIVIDUAL
                default:
                    return super.asType(clazz)
            }
        }
        return super.asType(clazz)
    }
}