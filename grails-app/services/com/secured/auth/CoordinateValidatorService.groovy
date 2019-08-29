package com.secured.auth

import grails.compiler.GrailsCompileStatic
import grails.gorm.transactions.Transactional

@Transactional
@GrailsCompileStatic
class CoordinateValidatorService implements  CoordinateValidator
{

    @Transactional(readOnly = true)
    @Override
    boolean isValidValueForPositionAndUserName(String v, String p, String name) {
        SecurityCoordinate.where {
            position == p && value == v && user.username == name
        }.count() as boolean
    }
}
