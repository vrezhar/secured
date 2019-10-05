package com.secured.signature

import com.secured.auth.User

class Signature
{
    String body

    static belongsTo = [user: User]

    static constraints = {
    }
}
