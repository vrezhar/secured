package com.ttreport.auth.signature


import ru.CryptoPro.JCP.JCP

import java.security.KeyStore
import java.security.cert.X509Certificate

class testCertificates
{
    static void performTest() {

        System.setProperty("ru.CryptoPro.reprov.enableCRLDP", "true")
        System.setProperty("com.sun.security.enableCRLDP", "true")
        System.setProperty("com.ibm.security.enableCRLDP", "true")
        final Collection<X509Certificate> chain = new ArrayList<>();
        final KeyStore ks = KeyStore.getInstance(JCP.HD_STORE_NAME, JCP.PROVIDER_NAME);
        ks.load(null, null);
    }

}
