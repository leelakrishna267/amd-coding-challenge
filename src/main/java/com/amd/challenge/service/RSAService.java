package com.amd.challenge.service;

import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class RSAService {

    public KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        return generator.generateKeyPair();
    }

    public String encodeKey(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public void generateAndSaveKeys() throws Exception {

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);

        KeyPair keyPair = generator.generateKeyPair();

        saveKey("keys/public.key", keyPair.getPublic().getEncoded());
        saveKey("keys/private.key", keyPair.getPrivate().getEncoded());
    }

    private void saveKey(String path, byte[] key) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(key);
        }
    }

    public PublicKey loadPublicKey(String filePath) throws Exception {

    // Step 1: Read key bytes from file
    byte[] keyBytes = Files.readAllBytes(Paths.get(filePath));

    // Step 2: Create key specification (X509 for public key)
    X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);

    // Step 3: Create KeyFactory for RSA
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");

    // Step 4: Generate PublicKey object
    return keyFactory.generatePublic(spec);
}

public PrivateKey loadPrivateKey(String filePath) throws Exception {

    byte[] keyBytes = Files.readAllBytes(Paths.get(filePath));

    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);

    KeyFactory keyFactory = KeyFactory.getInstance("RSA");

    return keyFactory.generatePrivate(
        spec);
}
}
