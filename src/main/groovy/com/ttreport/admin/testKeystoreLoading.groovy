package com.ttreport.admin

import java.security.KeyStore

class testKeystoreLoading
{
    public static test(){
        try{
            KeyStore ks
            ks = KeyStore.getInstance("HDImageSTore")
            ks.load(new FileInputStream("/certificates"),"test".toCharArray())
            println("keystore loaded")
        }
        catch (Exception e)
        {
            println(e.message)
        }
    }
}
