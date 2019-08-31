package com.secured.auth

import java.util.regex.*;
import grails.gorm.transactions.Transactional

@Transactional
class PatternValidatorService {


    int validatePassword(String password)
    {
        if(password.length() < 7)
            return 0
        if(password.length() > 16)
            return -1
        int strength = 0
        strength += validatePatternFor(".*\\d+.*",password);//contains at least one number
        strength += validatePatternFor(".*[A-Z]+.*",password);//contains at least one uppercase character
        strength += validatePatternFor(".*[a-z]+.*",password);//contains at least one lowercase character
        return strength
    }

    boolean validateUsername(String username)
    {
        //contains at least one lowercase character and has,has at least 3 characters but less than 16
        return validatePatternFor("[^0-9][a-zA-Z0-9]{2,15}",username) as boolean;
    }

    private static int validatePatternFor(String pattern, String what_is_being_validated)
    {
        Pattern _pattern = Pattern.compile(pattern)
        Matcher matcher = _pattern.matcher(what_is_being_validated)
        if(matcher.matches())
            return 1;
        return 0;
    }

}
