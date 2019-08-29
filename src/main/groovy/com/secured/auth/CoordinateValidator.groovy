package com.secured.auth

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
interface CoordinateValidator
{
    boolean isValidValueForPositionAndUserName(String v, String p, String name)
}