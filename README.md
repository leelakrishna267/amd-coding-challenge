# 🔐 Crypto Application – RSA + SHA-256 (Spring Boot)

## 📌 Overview

This application demonstrates:

* RSA key pair generation (2048-bit)
* File encryption & decryption using RSA
* SHA-256 hashing of files

It is built using **Spring Boot** and standard Java cryptography libraries.

---

## ⚙️ Features

### 1. RSA Key Generation

* Generates **2048-bit RSA key pair**
* Saves keys into:

  * `keys/public.key`
  * `keys/private.key`

---

### 2. File Encryption

* Encrypts file using **public key**
* Output stored as:

  * `files/encrypted.dat`

---

### 3. File Decryption

* Decrypts file using **private key**
* Output stored as:

  * `files/decrypted.txt`

---

### 4. SHA-256 Hashing

* Generates SHA-256 hash of any file
* Output in **HEX format**

---

## 📁 Project Structure

```
crypto-app/
│
├── keys/
│   ├── public.key
│   └── private.key
│
├── files/
│   ├── original.txt
│   ├── encrypted.dat
│   └── decrypted.txt
│
├── src/main/java/com/example/crypto/
│   ├── controller/
│   ├── service/
│
└── README.md
```

---

## 🚀 How to Run

### 1. Start Application

```bash
mvn spring-boot:run
```

---

## 🔗 API Endpoints

### ✅ Generate Keys

```
GET /crypto/generate-keys
(public.key/private.key files already generated, if you want re-geneare/run above url ?, then clear content in both files)
```

✔ Generates and saves:

* `public.key`
* `private.key`

---

### ✅ Encrypt File

```
GET /crypto/encrypt?input=files/original.txt&output=files/encrypted.dat
```

✔ Uses:

* `public.key`

---

### ✅ Decrypt File

```
GET /crypto/decrypt?input=files/encrypted.dat&output=files/decrypted.txt
```

✔ Uses:

* `private.key`

---

### ✅ Generate SHA-256 Hash

```
GET /crypto/hash?path=files/original.txt
```

✔ Returns HEX encoded hash

---

## 🔄 Execution Flow

```
1. Generate keys
2. Place file → files/original.txt
3. Encrypt file
4. Decrypt file
5. Compare files
6. Verify using SHA-256
```

---

## ✅ Verification

Ensure:

```
original.txt == decrypted.txt
```

OR compare hashes:

```
Hash(original.txt) == Hash(decrypted.txt)
```

---

## ⚠️ Important Observations

### 🔐 1. Same Key Pair Usage

* Keys must be generated **only once**
* Do NOT regenerate keys during encrypt/decrypt

---

### 🔐 2. Correct Key Usage

* Encryption → **Public Key**
* Decryption → **Private Key**

---

### 🔐 3. RSA Block Size Limitation (Critical)

For **2048-bit RSA**:

* Encryption block size → **245 bytes**
* Decryption block size → **256 bytes**

Incorrect handling leads to:

```
javax.crypto.BadPaddingException: Decryption error
```

---

### 🔐 4. Cipher Configuration

```
RSA/ECB/PKCS1Padding
```

Must be used consistently in both encrypt and decrypt.

---

### 🔐 5. Key Formats

| Key Type    | Format Used         |
| ----------- | ------------------- |
| Public Key  | X509EncodedKeySpec  |
| Private Key | PKCS8EncodedKeySpec |

---

## ❗ Common Errors & Fixes

### ❌ BadPaddingException

Cause:

* Different key pair used
* Wrong block size
* Corrupted encrypted file

Fix:

* Use same keys
* Maintain correct buffer sizes (245 / 256)

---

### ❌ Invalid Key

Cause:

* Wrong key format
* Incorrect loading method

Fix:

* Use proper KeySpec classes

---

## 💡 Limitations

* RSA is **not suitable for large files**
* This implementation uses **chunking**
* In real systems, RSA is used to encrypt symmetric keys (Hybrid Encryption)

---

## 🏁 Deliverables

The following files are included:

* `keys/public.key`
* `keys/private.key`
* `files/original.txt`
* `files/encrypted.dat`
* `files/decrypted.txt`

---

## 👨‍💻 Author

Implemented as part of coding challenge submission.

---
