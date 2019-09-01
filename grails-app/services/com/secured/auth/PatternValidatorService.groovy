package com.secured.auth

import java.util.regex.*;
import grails.gorm.transactions.Transactional

@Transactional
class PatternValidatorService {

    int validatePassword(String password)
    {
        if(password.length() < 7)
            return -2
        if(password.length() > 16)
            return -1
        int strength = 0
        strength += validatePatternFor("\\S*\\d+\\S*",password);//contains at least one number
        strength += validatePatternFor("\\S*[A-Z]+\\S*",password);//contains at least one uppercase character
        strength += validatePatternFor("\\S*[a-z]+\\S*",password);//contains at least one lowercase character
        return strength
    }

    boolean validateUsername(String username)
    {
        if(username.length()<3 || username.length()>16)
            return false
        //contains at least one lowercase character and has,has at least 3 characters but less than 16
        return validatePatternFor("[a-zA-Z]+\\S",username) as boolean;
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
