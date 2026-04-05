package com.amd.challenge.controller;

import com.amd.challenge.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

@RestController
@RequestMapping("/crypto")
public class CryptoController {

    @Autowired
    private RSAService rsaService;

    @Autowired
    private HashService hashService;

    @Autowired
    private FileCryptoService fileCryptoService;

    @GetMapping("/generate-keys")
    public String generateKeys() {
        try {
            rsaService.generateAndSaveKeys();
            return "✅ Keys generated successfully!";
        } catch (Exception e) {
            return "❌ Error: " + e.getMessage();
        }
    }

    @GetMapping("/hash")
    public String hashFile(@RequestParam String path) throws Exception {
        return hashService.generateSHA256(path);
    }

    @GetMapping("/encrypt")
    public String encrypt(@RequestParam String input,
                          @RequestParam String output) throws Exception {

        PublicKey publicKey = rsaService.loadPublicKey("keys/public.key");

        fileCryptoService.encryptFile(input, output, publicKey);

        return "File encrypted successfully";
    }

    @GetMapping("/decrypt")
    public String decrypt(@RequestParam String input,
                          @RequestParam String output) throws Exception {

        PrivateKey privateKey = rsaService.loadPrivateKey("keys/private.key");
        fileCryptoService.decryptFile(input, output, privateKey);

        return "File decrypted successfully";
    }
}
