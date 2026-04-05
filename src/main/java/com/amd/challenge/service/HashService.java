package com.amd.challenge.service;

import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.security.MessageDigest;

@Service
public class HashService {

    public String generateSHA256(String filePath) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        FileInputStream fis = new FileInputStream(filePath);
        byte[] byteArray = new byte[1024];
        int bytesCount;

        while ((bytesCount = fis.read(byteArray)) != -1) {
            digest.update(byteArray, 0, bytesCount);
        }

        fis.close();

        byte[] hashBytes = digest.digest();

        // Convert to HEX
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }
}
