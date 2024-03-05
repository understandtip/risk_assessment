package com.yushang.risk.common.util;

import com.yushang.risk.common.exception.BusinessException;
import com.yushang.risk.common.exception.CommonErrorEnum;
import com.yushang.risk.common.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * AES对称加密工具类
 *
 * @author zlp
 */
@Slf4j
public class AesUtil {
  /** 加密算法 */
  private static final String ALGORITHM = "AES";

  /** 算法/模式/补码方式 */
  private static final String ALGORITHM_PROVIDER = "AES/ECB/PKCS5Padding";

  /** 密码解密秘钥 */
  private static final String ENCRYPTION_KEY_FRONT = "uiw89xhdbnlpq98126yhj902bnmxygvv";
  /** 系统接口返回数据加密秘钥 */
  private static final String ENCRYPTION_KEY_ALL = "rB5ZlKzMgfhjWn6XuDQ9eHjN2c4Rp7aY";

  /**
   * 加密
   *
   * @param src 原内容
   * @param uniqueKey 唯一key
   * @return
   */
  private static String encrypt(String src, String uniqueKey) throws Exception {
    byte[] key = uniqueKey.getBytes();
    SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
    Cipher cipher = Cipher.getInstance(ALGORITHM_PROVIDER);
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    byte[] cipherBytes = cipher.doFinal(src.getBytes(StandardCharsets.UTF_8));
    return byteToHexString(cipherBytes);
  }

  /**
   * 解密
   *
   * @param enc 加密内容
   * @param uniqueKey 唯一key
   * @return
   */
  private static String decrypt(String enc, String uniqueKey) throws Exception {
    byte[] key = uniqueKey.getBytes();
    SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
    Cipher cipher = Cipher.getInstance(ALGORITHM_PROVIDER);
    cipher.init(Cipher.DECRYPT_MODE, secretKey);
    byte[] hexBytes = hexStringToBytes(enc);
    byte[] plainBytes = cipher.doFinal(hexBytes);
    return new String(plainBytes);
  }

  /**
   * 将byte数组转换为16进制字符串
   *
   * @param src
   * @return
   */
  private static String byteToHexString(byte[] src) {
    return Hex.encodeHexString(src);
  }

  /**
   * 将16进制字符串转换为byte数组
   *
   * @param hexString
   * @return
   */
  private static byte[] hexStringToBytes(String hexString) throws DecoderException {
    return Hex.decodeHex(hexString);
  }

  /**
   * 第一阶段解密前端的密文
   *
   * @param encrypt
   * @return
   */
  public static String decryptPassword(String encrypt) {
    try {
      String decrypt = decrypt(encrypt, ENCRYPTION_KEY_FRONT);
      return removeQuotes(decrypt);
    } catch (Exception e) {
      throw new SystemException(CommonErrorEnum.SYSTEM_ERROR.getErrorCode(), "第一阶段密码解密失败,抛出异常");
    }
  }

  /**
   * 加密数据
   *
   * @param data
   * @return
   */
  public static String encryptData(String data) {
    try {
      return encrypt(data, ENCRYPTION_KEY_ALL);
    } catch (Exception e) {
      log.error("加密出错", e);
      throw new BusinessException("加密出错");
    }
  }

  private static String removeQuotes(String input) {
    return input.replace("\"", "");
  }

}
