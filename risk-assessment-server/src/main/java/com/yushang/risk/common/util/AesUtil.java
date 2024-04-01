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

  public static void main(String[] args) throws Exception {
    String a =
        decrypt(
            "1f2739b75a8b5295e78d63230b77981a6c9b97f28fb46a9cd6845cb8f7dfdece068a7e5e8ad36c62c6e6b426fa5b411d8ed4f23c6df9f82d4f0e99c76b375a59091beca8fce0e5e83a537c57397beadf4856f9c568ec9b89eea49657cb9f03461cf72f89375d5366c919c2520601c8aedf012a76f7dc1f1042dcaa767327be57cbe0eb6a5dd60d98f0423b8255fdf4cbf445cfe76aa177816723382321a85a0c33d428681405f946c9a5fec887d8052be2cd475ea8580139dcf40b88e7b9826dcff58360baa3f7a35c1ac77b0dfa889034b3f515078d1fbead8d5cbfe932dad41e3e3b49d51539b300ca69348f31d38fe2e75d46513be3d86bbaf32300b0cf79faf1b532245b1f6fedfcb76bae77105be1fa0f8b59606b2002acdd2ee980b9d158f44e817e89ec701f42a0929294cc05034daa0c2f40cc496ba835070497e1d1809bfc9af946d78a989c51be77e9b3289e962a0de70e4158fd4223f4285ec92e70d13cf0db78cd679e2d850f01a4fd07fdd3521a2fb890bf150a25d0d61a2d391d8ab71f0b61e343a57085c91294366432cedb9a4c51e8889b2a844088afcb4e15e5130af6dda34c9412cb11f213925ea5b5d6967a5fd4da0c0b5e813b2936e3c54bd86bf24f993cfa519b275c2d8aff37930bda307081ca87f418b19628df0595b48b930d43d363486e0f57a78db84577da61ef2bb2f599607d4609979387b6585703b67bde16c88146415887338f1150cc9a1622b1bb75b06ff6587d7613fb34b63e558f7dd3d6f9dddac5fc706b5b170bd76431637058ae50d6075e28734316c9c4e658666f62326a05b6c1cc522cd3d2896c8f56484d42b6bc713df57f2c",
            ENCRYPTION_KEY_ALL);
    System.out.println("a = " + a);
  }
}
