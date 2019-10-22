package com.ttreport.api.response

import grails.config.Config
import grails.core.support.GrailsConfigurationAware

class Errors implements GrailsConfigurationAware
{
    protected static Map statusCodes = [:]

    @Override
    void setConfiguration(Config co)
    {
        statusCodes.id_not_found = co.status.INVALID_PRODUCT_ID
        statusCodes.invalid_document = co.status.INVALID_DOCUMENT
        statusCodes.code_not_found = co.status.CODE_NOT_FOUND
        statusCodes.invalid_uit = co.status.INVALID_PRODUCT_UIT_CODE
        statusCodes.invalid_uitu = co.status.INVALID_PRODUCT_UITU_CODE
        statusCodes.code_deleted = co.status.DELETED_BAR_CODE
    }
    static int invalidDocument(){return statusCodes.invalid_document as int}
    static int invalidUitCode(){return statusCodes.invalid_uit as int}
    static int invalidUituCode(){return statusCodes.invalid_uitu as int}
    static int idNotFound(){return statusCodes.id_not_found as int}
    static int codeNotFound(){return statusCodes.code_not_found as int}
    static int codeDeleted(){return statusCodes.code_deleted as int}
}
