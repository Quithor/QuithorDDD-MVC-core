package top.yanquithor.framework.dddbase.common.infrastructure.util;

import com.alibaba.fastjson2.JSON;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RSA asymmetric encryption utility class for secure data encryption and decryption.
 * Automatically manages RSA key pair generation, storage, and loading.
 * Provides both string-based and object-based encryption/decryption methods.
 * Designed for use in DDD-based applications with security requirements.
 *
 * @author YanQuithor
 * @since 2025-10-29
 */
@Slf4j
public class RsaCryptoUtil {
    
    private final String keyDirectory;
    
    private KeyPair keyPair;
    
    /**
     * Constructor that injects the key storage directory path.
     *
     * @param keyDirectory The directory path where key files are stored
     */
    public RsaCryptoUtil(@Value("${app.crypto.key-directory}") String keyDirectory) {
        this.keyDirectory = keyDirectory;
    }
    
    /**
     * Initialization method executed after Spring container initialization is complete.
     * Loads keys from files if they exist; otherwise generates a new key pair and saves it to disk.
     *
     * @throws Exception Exception that may be thrown during initialization
     */
    @PostConstruct
    public void init() throws Exception {
        Path dirPath = Paths.get(keyDirectory);
        String PUBLIC_KEY_FILE = "public.key";
        Path pubPath = dirPath.resolve(PUBLIC_KEY_FILE);
        String PRIVATE_KEY_FILE = "private.key";
        Path priPath = dirPath.resolve(PRIVATE_KEY_FILE);
        
        // 如果目录不存在则创建
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        
        // 尝试从文件加载密钥
        if (Files.exists(pubPath) && Files.exists(priPath)) {
            loadKeysFromFile(pubPath, priPath);
            log.info("Loaded RSA key pair from: {}", dirPath.toAbsolutePath());
        } else {
            // 生成新的密钥对
            generateAndSaveKeyPair(pubPath, priPath);
            log.info("RSA key pair generated and saved to: {}", dirPath.toAbsolutePath());
        }
    }
    
    /**
     * Load public and private key files from specified paths and build KeyPair object.
     *
     * @param pubPath Public key file path
     * @param priPath Private key file path
     * @throws Exception Exception that may be thrown during key loading
     */
    private void loadKeysFromFile(Path pubPath, Path priPath) throws Exception {
        byte[] pubBytes = Files.readAllBytes(pubPath);
        byte[] priBytes = Files.readAllBytes(priPath);
        
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(pubBytes));
        PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(priBytes));
        
        this.keyPair = new KeyPair(publicKey, privateKey);
    }
    
    /**
     * Generate a new RSA key pair and save the public and private keys to specified paths.
     *
     * @param pubPath Public key save path
     * @param priPath Private key save path
     * @throws Exception Exception that may be thrown during generation or saving of keys
     */
    private void generateAndSaveKeyPair(Path pubPath, Path priPath) throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        
        this.keyPair = generator.generateKeyPair();
        
        // 保存公密钥 (X.509)
        try (FileOutputStream fos = new FileOutputStream(pubPath.toFile())) {
            fos.write(this.keyPair.getPublic().getEncoded());
        }
        
        // 保存私钥 (PKCS#8)
        try (FileOutputStream fos = new FileOutputStream(priPath.toFile())) {
            fos.write(this.keyPair.getPrivate().getEncoded());
        }
    }
    
    /**
     * Encrypt string data using public key and return Base64 encoded encryption result.
     *
     * @param data Raw string to be encrypted
     * @return Base64 string after encryption
     * @throws Exception Exception that may be thrown during encryption
     */
    public String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());
        byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
    
    /**
     * Decrypt Base64 encoded encrypted data using private key and return original string.
     *
     * @param encryptedData Base64 string after encryption
     * @return Original string after decryption
     * @throws Exception Exception that may be thrown during decryption
     */
    public String decrypt(String encryptedData) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
    
    /**
     * Encrypt any object using public key. The object will first be serialized to JSON string before encryption.
     *
     * @param data Object to be encrypted
     * @return Base64 string after encryption
     */
    public String encrypt(Object data) {
        try {
            return encrypt(JSON.toJSONString(data));
        } catch (Exception e) {
            log.error("Error occurred while encrypting data: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Decrypt Base64 encoded encrypted data using private key and deserialize the result to target type object.
     *
     * @param encryptedData Base64 string after encryption
     * @param clazz         Class object of target type
     * @param <T>           Generic parameter representing target type
     * @return Object after decryption and deserialization
     */
    public <T> T decrypt(String encryptedData, Class<T> clazz) {
        try {
            return JSON.parseObject(decrypt(encryptedData), clazz);
        } catch (Exception e) {
            log.error("Error occurred while decrypting data: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
