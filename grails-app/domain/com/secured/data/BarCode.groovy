package com.secured.data

class BarCode {

    //ToDo
    //Implement this properly later
    String code
    Date dateCreated
    Date lastUpdated
    Date dateDeleted = null

    static belongsTo = [company: Company]
    static constraints = {
        dateDeleted nullable: true
    }
}
