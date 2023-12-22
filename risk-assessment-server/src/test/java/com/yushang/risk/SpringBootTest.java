package com.yushang.risk;

import com.yushang.risk.common.util.PasswordUtils;
import com.yushang.risk.common.util.RedisUtils;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * @Author：zlp @Package：com.yushang.risk @Project：risk_assessment
 *
 * @name：My @Date：2023/12/21 13:42 @Filename：My
 */
@org.springframework.boot.test.context.SpringBootTest
public class SpringBootTest {
  @Test
  void testRedis() {
    RedisUtils.set("k1", "v1");
  }

  public static void main(String[] args) {
    String randomString = generateRandomString();
    System.out.println("Random String: " + randomString);
  }

  public static String generateRandomString() {
    String characters = "0123456789abcdefghijklmnopqrstuvwxyz";
    StringBuilder stringBuilder = new StringBuilder();
    Random random = new Random();

    for (int i = 0; i < 32; i++) {
      int randomIndex = random.nextInt(characters.length());
      char randomChar = characters.charAt(randomIndex);
      stringBuilder.append(randomChar);
    }

    return stringBuilder.toString();
  }
}
