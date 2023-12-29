package com.yushang.risk;

import com.yushang.risk.common.util.PasswordUtils;
import org.junit.jupiter.api.Test;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author：zlp @Package：com.yushang.risk @Project：risk_assessment
 *
 * @name：NormalTest @Date：2023/12/21 15:21 @Filename：NormalTest
 */
public class NormalTest {

  private static final String AES_ALGORITHM = "AES";

  public static SecretKey generateSecretKey(String key) throws NoSuchAlgorithmException {
    byte[] keyBytes = key.getBytes();
    MessageDigest sha = MessageDigest.getInstance("SHA-256");
    keyBytes = sha.digest(keyBytes);
    keyBytes = java.util.Arrays.copyOf(keyBytes, 16); // Use only first 128 bit
    return new SecretKeySpec(keyBytes, AES_ALGORITHM);
  }

  public static byte[] encrypt(String plaintext, SecretKey secretKey)
      throws NoSuchPaddingException,
          NoSuchAlgorithmException,
          InvalidAlgorithmParameterException,
          InvalidKeyException,
          BadPaddingException,
          IllegalBlockSizeException {
    Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    return cipher.doFinal(plaintext.getBytes());
  }

  public static String decrypt(byte[] ciphertext, SecretKey secretKey)
      throws NoSuchPaddingException,
          NoSuchAlgorithmException,
          InvalidAlgorithmParameterException,
          InvalidKeyException,
          BadPaddingException,
          IllegalBlockSizeException {
    Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
    cipher.init(Cipher.DECRYPT_MODE, secretKey);
    byte[] decryptedBytes = cipher.doFinal(ciphertext);
    return new String(decryptedBytes);
  }

  public static void main(String[] args)
      throws NoSuchAlgorithmException,
          NoSuchPaddingException,
          InvalidKeyException,
          BadPaddingException,
          IllegalBlockSizeException,
          InvalidAlgorithmParameterException {
    String password = "123456";
    String encryptionKey = "YourEncryptionKey"; // 16 or 32 characters

    // Generate secret key
    SecretKey secretKey = generateSecretKey(encryptionKey);

    // Encrypt password
    byte[] encryptedPassword = encrypt(password, secretKey);
    System.out.println("加密 Password: " + new String(encryptedPassword));

    // Decrypt password
    String decryptedPassword = decrypt(encryptedPassword, secretKey);
    System.out.println("解密 Password: " + decryptedPassword);
  }

  @Test
  void testEncryptPassword() throws Exception {
    String s = PasswordUtils.encryptPassword("123456");
    System.out.println("PasswordUtils.encryptPassword(\"123456\") = " + s);
    System.out.println("PasswordUtils.decryptPassword(s) = " + PasswordUtils.decryptPassword(s));
  }

  @Test
  void test01() {
    String s = "1/fosajdflajksdfl";
    int index = s.lastIndexOf("/");
    System.out.println("s.substring(index) = " + s.substring(index + 1));
  }

  @Test
  void test02() {
    System.out.println("a() = " + a());
  }

  boolean a() {
    return false && false;
  }
}
