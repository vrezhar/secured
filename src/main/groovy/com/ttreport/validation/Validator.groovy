package com.ttreport.validation

import java.util.regex.Matcher
import java.util.regex.Pattern

class Validator
{
    private Validator(){}

    static validateBarCodeFormat(String code)
    {
        Pattern pattern = Pattern.compile("01[0-9]{14}21[-&>'()*+,./_:;=<?!%a-zA-Z0-9]{13}")
        Matcher matcher = pattern.matcher(code)
        return matcher.matches()
    }
}
