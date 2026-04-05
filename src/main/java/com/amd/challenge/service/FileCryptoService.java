package com.amd.challenge.service;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.io.*;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;

@Service
public class FileCryptoService {

    private static final String ALGO = "RSA";

    // public void encryptFile(String inputFile, String outputFile, Key publicKey) throws Exception {
    //     processFile(inputFile, outputFile, publicKey, Cipher.ENCRYPT_MODE);
    // }

    public void encryptFile(String inputFile, String outputFile, PublicKey publicKey) throws Exception {

    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
    cipher.init(Cipher.ENCRYPT_MODE, publicKey);

    try (FileInputStream fis = new FileInputStream(inputFile);
         FileOutputStream fos = new FileOutputStream(outputFile)) {

        byte[] buffer = new byte[245]; // max for 2048-bit
        int bytesRead;

        while ((bytesRead = fis.read(buffer)) != -1) {
            byte[] encrypted = cipher.doFinal(buffer, 0, bytesRead);
            fos.write(encrypted);
        }
    }
}



    // public void decryptFile(String inputFile, String outputFile, Key privateKey) throws Exception {
    //     processFile(inputFile, outputFile, privateKey, Cipher.DECRYPT_MODE);
    // }

    public void decryptFile(String inputFile, String outputFile, PrivateKey privateKey) throws Exception {

    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
    cipher.init(Cipher.DECRYPT_MODE, privateKey);

    try (FileInputStream fis = new FileInputStream(inputFile);
         FileOutputStream fos = new FileOutputStream(outputFile)) {

        byte[] buffer = new byte[256]; // FIXED SIZE
        int bytesRead;

        while ((bytesRead = fis.read(buffer)) != -1) {

            if (bytesRead != 256) {
                throw new RuntimeException("Invalid encrypted block size: " + bytesRead);
            }

            byte[] decrypted = cipher.doFinal(buffer);
            fos.write(decrypted);
        }
    }
}

    private void processFile(String inputFile, String outputFile, Key key, int mode) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(mode, key);

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile)) {

            byte[] buffer = new byte[245]; // for 2048-bit RSA
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] output = cipher.doFinal(buffer, 0, bytesRead);
                fos.write(output);
            }
        }
    }
}
