package com.secured.data

class BarCode {

    //ToDo
    //Implement this properly later
    String code
    Date dateCreated
    Date lastUpdated

    static belongsTo = [company: Company]
    static constraints = {
    }
}
