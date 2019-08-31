package com.secured.auth

class BarCode {

    //@ToDO
    //Implement this properly later
    String code
    static belongsTo = [company: Company]
    static constraints = {
    }
}
