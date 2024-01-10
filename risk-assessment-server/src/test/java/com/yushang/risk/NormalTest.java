package com.yushang.risk;

import com.yushang.risk.common.util.PasswordUtils;
import org.apache.commons.codec.binary.Base64;
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
    String password = "123";
    String encryptionKey = "hicoqt19o3rgkts3upcalx7qel0uzxzu"; // 16 or 32 characters

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
    // String s = PasswordUtils.encryptPassword("123");
    // System.out.println("PasswordUtils.encryptPassword(\"123456\") = " + s);
    System.out.println(
        "PasswordUtils.decryptPassword(s) = "
            + PasswordUtils.decryptPassword("Mm93iASNtFMLcy+g2Y7OiA=="));
  }

  @Test
  void test01() {
    String s = "1/fosajdflajksdfl";
    int index = s.lastIndexOf("/");
    System.out.println("s.substring(index) = " + s.substring(index + 1));
  }

  @Test
  public void testDES() throws Exception {
    // 明文
    String plainText = "abcd";
    System.out.println("明文：" + plainText);

    // 提供原始秘钥:长度64位,8字节
    String originKey = "8jxn9wod";
    // 根据给定的字节数组构建一个秘钥
    SecretKeySpec key = new SecretKeySpec(originKey.getBytes(), "DES");

    // 加密
    // 1.获取加密算法工具类
    Cipher cipher = Cipher.getInstance("DES");
    // 2.对工具类对象进行初始化,
    // mode:加密/解密模式
    // key:对原始秘钥处理之后的秘钥
    cipher.init(Cipher.ENCRYPT_MODE, key);
    // 3.用加密工具类对象对明文进行加密
    // byte[] encipherByte = cipher.doFinal(plainText.getBytes());
    // 防止乱码，使用Base64编码
    // String encode = Base64.encodeBase64String(encipherByte);
    // System.out.println("加密：" + encode);

    // 解密
    // 2.对工具类对象进行初始化
    cipher.init(Cipher.DECRYPT_MODE, key);
    // 3.用加密工具类对象对密文进行解密
    byte[] decode = Base64.decodeBase64("2PhvANIh/08=");
    byte[] decipherByte = cipher.doFinal(decode);
    String decipherText = new String(decipherByte);
    System.out.println("解密：" + decipherText);
  }
}
