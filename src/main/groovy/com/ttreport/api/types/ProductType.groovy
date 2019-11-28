package com.ttreport.api.types

enum ProductType
{
    SIMPLE,
    EXTENDED

    Object asType(Class clazz)
    {
        if(clazz == CommandType.class){
            if(this == SIMPLE){
                return CommandType.PRODUCT
            }
            if(this == EXTENDED){
                return CommandType.EXTENDED_PRODUCT
            }
        }
        return super.asType(clazz)
    }
}