package com.ttreport

import com.ttreport.data.Document
import grails.gorm.transactions.Transactional

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.security.*
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec

@Transactional
class Base64EncoderService {

    private static final String keystore_path = "/test"
    private static final keystore_alias = "test"
    private static final keystore_password = "test"
    private static final String private_key_path = "/home/vrezh/snap/skype/common/header.key"
    private final static String public_key_path = "/home/vrezh/snap/skype/common/primary.key"

    String serializeAsJson(Document document)
    {
       return Document.serializeAsJson(document)
    }

    String encodeAsBase64(Document document)
    {
        return serializeAsJson(document).bytes.encodeAsBase64()
    }

    String decodeBase64(String input){
        return new String(input.decodeBase64())
    }

    def sign(String data = "test") throws Exception
    {

    }

    protected static KeyPair getKeyPair()
    {
        return new KeyPair(readPublicKey(public_key_path),readPrivateKey())
    }

    static byte[] readFileBytes(String filename) throws IOException
    {
        Path path = Paths.get(filename);
        return Files.readAllBytes(path);
    }

    static PublicKey readPublicKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
    {
        X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(readFileBytes(filename));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(publicSpec);
    }

    static PrivateKey readPrivateKey(String filename) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
    {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(readFileBytes(filename));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }
}
