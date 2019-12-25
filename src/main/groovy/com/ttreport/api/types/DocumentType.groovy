package com.ttreport.api.types

enum DocumentType
{
    ENTRANCE ,
    RELEASE ,
    ACCEPTANCE ,
    SHIPMENT ,
    INDIVIDUAL ,
    CANCEL_SHIPMENT,
    REMAINS_DESCRIPTION,
    FULL_REMAINS_DESCRIPTION,
    REMAINS_REGISTRY

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
                case REMAINS_DESCRIPTION:
                    return Endpoint.REMAINS_DESCRIPTION
                case FULL_REMAINS_DESCRIPTION:
                    return Endpoint.FULL_REMAINS_DESCRIPTION
                case REMAINS_REGISTRY:
                    return Endpoint.REMAINS_REGISTRY
                default:
                    return super.asType(clazz)
            }
        }
        return super.asType(clazz)
    }
}