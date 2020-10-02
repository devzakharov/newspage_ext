package com.zrv.newspage.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class CryptoService {

    private static CryptoService instance;
    MessageDigest messageDigest;

    {
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private CryptoService() {
    }

    public static CryptoService getInstance() {

        if (instance == null) {
            instance = new CryptoService();
        }
        return instance;
    }

    public String getHashString(String originalString) {

        byte[] encodedHash = messageDigest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedHash);
    }

    public Boolean compareHashStrings(String str1, String str2) {

        return str1.equals(str2);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
