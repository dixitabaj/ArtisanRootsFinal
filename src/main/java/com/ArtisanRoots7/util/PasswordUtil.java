package com.ArtisanRoots7.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class PasswordUtil {
    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128; // must be one of {128, 120, 112, 104, 96}
    private static final int IV_LENGTH_BYTE = 12;
    private static final int SALT_LENGTH_BYTE = 16;
    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    /**
     * Generates a random nonce (number used once) of specified length in bytes.
     *
     * @param numBytes the number of bytes for the nonce
     * @return a byte array containing the random nonce
     */
    public static byte[] getRandomNonce(int numBytes) {
        byte[] nonce = new byte[numBytes];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    }

    /**
     * Generates a new AES secret key with the specified key size.
     *
     * @param keysize the key size in bits (e.g., 128, 192, 256)
     * @return a SecretKey for AES encryption
     * @throws NoSuchAlgorithmException if AES algorithm or strong secure random is not available
     */
    public static SecretKey getAESKey(int keysize) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(keysize, SecureRandom.getInstanceStrong());
        return keyGen.generateKey();
    }

    /**
     * Derives an AES 256-bit secret key from a password and salt using PBKDF2 with HMAC SHA-256.
     *
     * @param password the password as a character array
     * @param salt     the salt as a byte array
     * @return a SecretKey derived from the password and salt, or null if an error occurs
     */
    public static SecretKey getAESKeyFromPassword(char[] password, byte[] salt) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            // iterationCount = 65536, keyLength = 256 bits
            KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
            SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
            return secret;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(PasswordUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Encrypts the given password using AES encryption with a key derived from the email.
     *
     * @param email    the email used to derive the AES encryption key (acts like a password)
     * @param password the password string to be encrypted
     * @return the base64 encoded encrypted text containing IV, salt, and ciphertext; or null if encryption fails
     */
    public static String encrypt(String email, String password) {
        try {
            // Generate 16-byte salt
            byte[] salt = getRandomNonce(SALT_LENGTH_BYTE);

            // Generate 12-byte IV for GCM
            byte[] iv = getRandomNonce(IV_LENGTH_BYTE);

            // Derive secret key from email and salt
            SecretKey aesKeyFromPassword = getAESKeyFromPassword(email.toCharArray(), salt);

            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);

            // Initialize cipher in encrypt mode with GCM parameters
            cipher.init(Cipher.ENCRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

            // Encrypt the password bytes
            byte[] cipherText = cipher.doFinal(password.getBytes(UTF_8));

            // Concatenate IV + salt + ciphertext
            byte[] cipherTextWithIvSalt = ByteBuffer.allocate(iv.length + salt.length + cipherText.length)
                    .put(iv)
                    .put(salt)
                    .put(cipherText)
                    .array();

            // Encode to base64 for storage/transmission
            return Base64.getEncoder().encodeToString(cipherTextWithIvSalt);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Decrypts the encrypted password using AES decryption with the key derived from username.
     *
     * @param encryptedPassword the base64 encoded encrypted password string (IV + salt + ciphertext)
     * @param username          the username used to derive the AES decryption key
     * @return the decrypted password as a string, or null if decryption fails
     */
    public static String decrypt(String encryptedPassword, String username) {
        try {
            byte[] decode = Base64.getDecoder().decode(encryptedPassword.getBytes(UTF_8));

            // Extract IV, salt, and ciphertext from the byte buffer
            ByteBuffer bb = ByteBuffer.wrap(decode);

            byte[] iv = new byte[IV_LENGTH_BYTE];
            bb.get(iv);

            byte[] salt = new byte[SALT_LENGTH_BYTE];
            bb.get(salt);

            byte[] cipherText = new byte[bb.remaining()];
            bb.get(cipherText);

            // Derive AES key from username and salt
            SecretKey aesKeyFromPassword = PasswordUtil.getAESKeyFromPassword(username.toCharArray(), salt);

            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);

            // Initialize cipher in decrypt mode with GCM parameters
            cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

            // Decrypt and return plaintext password
            byte[] plainText = cipher.doFinal(cipherText);

            return new String(plainText, UTF_8);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
