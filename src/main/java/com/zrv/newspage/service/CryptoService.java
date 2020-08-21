package com.zrv.newspage.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class CryptoService {

    private static CryptoService instance;
    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

    private CryptoService() throws NoSuchAlgorithmException {
    }

    public static CryptoService getInstance() throws NoSuchAlgorithmException {
        if (instance == null) {
            instance = new CryptoService();
        }
        return instance;
    }

    public String getHashString(String originalString) {

        byte[] encodedhash = messageDigest.digest(originalString.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedhash);
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
