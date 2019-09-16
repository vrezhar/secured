package com.secured.auth

class Company
{
    String address
    String token = UUID.randomUUID().toString()
    Date dateCreated
    Date lastUpdated

    static belongsTo = [user: User]
    static hasMany = [barCodes: BarCode]

    static constraints = {
        token nullable: false, unique: true
    }
}
