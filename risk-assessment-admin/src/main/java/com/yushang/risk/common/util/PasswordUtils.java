package com.yushang.risk.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Author：zlp @Package：com.yushang.risk.common.util @Project：risk_assessment
 *
 * @name：PasswordUtils @Date：2023/12/21 15:33 @Filename：PasswordUtils
 */
public class PasswordUtils {
  /** 加密算法 */
  private static final String AES_ALGORITHM = "AES";
  /** 秘钥 */
  public static final String ENCRYPTION_KEY = "hicoqt19o3rgkts3upcalx7qel0uzxzu";

  public static SecretKey generateSecretKey(String key) throws NoSuchAlgorithmException {
    byte[] keyBytes = key.getBytes();
    MessageDigest sha = MessageDigest.getInstance("SHA-256");
    keyBytes = sha.digest(keyBytes);
    keyBytes = java.util.Arrays.copyOf(keyBytes, 32);
    return new SecretKeySpec(keyBytes, AES_ALGORITHM);
  }

  public static byte[] encrypt(String plaintext, SecretKey secretKey) throws Exception {
    Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    return cipher.doFinal(plaintext.getBytes());
  }

  public static String decrypt(byte[] ciphertext, SecretKey secretKey) throws Exception {
    Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
    cipher.init(Cipher.DECRYPT_MODE, secretKey);
    byte[] decryptedBytes = cipher.doFinal(ciphertext);
    return new String(decryptedBytes);
  }

  public static String bytesToHex(byte[] bytes) {
    StringBuilder hexString = new StringBuilder();
    for (byte b : bytes) {
      String hex = Integer.toHexString(0xff & b);
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString();
  }

  public static byte[] hexToBytes(String hexString) {
    byte[] bytes = new byte[hexString.length() / 2];
    for (int i = 0; i < hexString.length(); i += 2) {
      bytes[i / 2] = (byte) Integer.parseInt(hexString.substring(i, i + 2), 16);
    }
    return bytes;
  }

  /**
   * 加密密码
   *
   * @param password
   * @return
   * @throws Exception
   */
  public static String encryptPassword(String password) throws Exception {
    SecretKey secretKey = generateSecretKey(ENCRYPTION_KEY);
    byte[] encryptedPassword = encrypt(password, secretKey);
    return bytesToHex(encryptedPassword);
  }

  /**
   * 解密密码
   *
   * @param password
   * @return
   * @throws Exception
   */
  public static String decryptPassword(String password) throws Exception {
    SecretKey secretKey = generateSecretKey(ENCRYPTION_KEY);
    byte[] encryptedPasswordBytes = hexToBytes(password);
    return decrypt(encryptedPasswordBytes, secretKey);
  }

  /*   public static void main(String[] args) throws Exception {
    // String s = encryptPassword("ydhcnbmfio0283Z");//0d06bdd3e1ef996f73dc42913dc95439
    // String s = encryptPassword("123"); // 647c5892867fcfaaf374ebf85e3b97f9
    System.out.println("s = " + s);
  } */
}
