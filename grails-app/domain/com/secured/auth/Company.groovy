package com.secured.auth

class Company
{
    String companyId
    String companyToken
    String address
    Date dateCreated
    static belongsTo = [user: User]
    static hasMany = [barCodes: BarCode]
    static constraints = {
    }
}
