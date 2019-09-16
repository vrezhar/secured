package com.secured.data

import com.secured.auth.User
import com.secured.data.BarCode

class Company
{
    String address
    String companyId
    String token = UUID.randomUUID().toString()
    Date dateCreated
    Date lastUpdated

    static belongsTo = [user: User]
    static hasMany = [barCodes: BarCode]

    static constraints = {
        token nullable: false, unique: true
    }
}
