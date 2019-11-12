package com.ttreport.datacentre

import com.ttreport.data.Document
import com.ttreport.logs.DevCycleLogger
import grails.gorm.transactions.Transactional
import org.bouncycastle.asn1.DERSet
import org.bouncycastle.asn1.cms.Attribute
import org.bouncycastle.asn1.cms.AttributeTable
import org.bouncycastle.asn1.cms.CMSAttributes
import org.bouncycastle.asn1.cms.Time
import org.bouncycastle.cert.X509CertificateHolder
import org.bouncycastle.util.CollectionStore
import ru.CryptoPro.CAdES.CAdESType
import ru.CryptoPro.Crypto.CryptoProvider
import ru.CryptoPro.reprov.RevCheck

import java.security.NoSuchAlgorithmException
import java.security.Security
import java.security.KeyStore
import java.security.PrivateKey
import java.security.cert.Certificate
import ru.CryptoPro.JCP.JCP
import ru.CryptoPro.CAdES.CAdESSignature

import java.security.cert.CertificateFactory
import java.security.spec.InvalidKeySpecException


@Transactional
class SigningService {

    protected static final String keystore_type = JCP.HD_STORE_NAME
    protected static final String keystore_alias = "test"
    protected static final char[] keystore_password = null
    protected static final Integer signature_type = CAdESType.CAdES_BES
    protected static final String digest_oid =  JCP.GOST_DIGEST_2012_256_OID
    protected static final String key_oid = JCP.GOST_PARAMS_EXC_2012_256_KEY_OID
    protected static final String provider = JCP.PROVIDER_NAME

    protected static String serializeAsJson(Document document)
    {
       return Document.serializeAsJson(document)
    }

    def sign(byte[] data = "a".getBytes(), boolean detached = false) throws Exception
    {
        System.setProperty("com.sun.security.enableAIAcaIssuers", "true")
        System.setProperty("com.sun.security.enableCRLDP","true")
        Security.addProvider(new JCP())
        Security.addProvider(new RevCheck())
        Security.addProvider(new CryptoProvider())

        KeyStore keyStore = KeyStore.getInstance(keystore_type)
        byte[] signedCode = null
        try {
            keyStore.load(null, null)
            PrivateKey key = (PrivateKey) keyStore.getKey(keystore_alias, keystore_password)
            List<Certificate> certificates = []
            Arrays.asList(keyStore.getCertificateChain(keystore_alias)).each {
                certificates.add(it)
            }
            certificates.add(readCertificate("/certs/tensor.crt"))
            certificates.add(readCertificate("/certs/CA.crt"))
            List<X509CertificateHolder> x509Certificates = new ArrayList<X509CertificateHolder>()
            DevCycleLogger.log("key is ${key.toString()}")
            certificates.each {
                try{
                    x509Certificates.add(new X509CertificateHolder(it.getEncoded()))
                }
                catch (Exception e)
                {
                    DevCycleLogger.log("exception occurred while building x509 certificate, message: ${e.message}")
                }

            }
            x509Certificates.each {
                println(it.toString())
            }
            println(certificates.size())
            CAdESSignature signature = new CAdESSignature(false)
            ByteArrayOutputStream out = new ByteArrayOutputStream()
            signature.setCertificateStore(new CollectionStore(x509Certificates))
            final Hashtable table = new Hashtable()
            Attribute attr = new Attribute(CMSAttributes.signingTime, new DERSet(new Time(new Date())))
            table.put(attr.getAttrType(), attr)
            AttributeTable attrTable = new AttributeTable(table)
            signature.addSigner(provider,
                    digest_oid,
                    key_oid,
                    key,
                    certificates,
                    signature_type,
                    null,
                    false,
                    attrTable,
                    null);
            signature.open(out)
            signature.update(data)
            signature.close()
            signedCode = out.toByteArray()
        }
        catch (Exception e)
        {
            DevCycleLogger.log("exception occurred, error message:")
            DevCycleLogger.log(e.message)
            DevCycleLogger.log("stack trace:")
            e.stackTrace.each {
                DevCycleLogger.log(it.toString())
            }
        }
        DevCycleLogger.log(signedCode?.encodeBase64()?.toString())
        DevCycleLogger.print_logs()
        DevCycleLogger.cleanup()
        return signedCode
    }

    protected static Certificate readCertificate(String fileName) throws  Exception
    {
        FileInputStream fis = null
        BufferedInputStream bis = null
        Certificate cert
        try {
            fis = new FileInputStream(fileName)
            bis = new BufferedInputStream(fis)
            final CertificateFactory cf = CertificateFactory.getInstance("X.509")
            cert = cf.generateCertificate(bis)
            return cert
        }
        finally {
            println("cleaning up streams")
            if (bis != null) {
                bis.close()
                DevCycleLogger.log("bytestream closed")
            }
            if (fis != null){
                fis.close()
                DevCycleLogger.log("filestream closed")
            }
        }
    }
}
