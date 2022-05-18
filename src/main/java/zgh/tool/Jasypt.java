package zgh.tool;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.text.BasicTextEncryptor;

/**
 * Jasypt
 */
public class Jasypt {

  public static void main(String[] args) {
    String key = "hnkc";
    String str = "110110abcd阿斯蒂芬";

    String secret = Jasypt.encrypt(key, str);
    String ori = Jasypt.decrypt(key, secret);

    System.out.println("str: " + str);
    System.out.println("secret: " + secret);
    System.out.println("ori: " + ori);
  }

  public static String encrypt(String key, String str) {
    BasicTextEncryptor encryptor = new BasicTextEncryptor();
    encryptor.setPassword(key);
    return encryptor.encrypt(str);
  }

  public static String decrypt(String key, String str) {
    BasicTextEncryptor encryptor = new BasicTextEncryptor();
    encryptor.setPassword(key);
    try {
      return encryptor.decrypt(str);
    } catch (EncryptionOperationNotPossibleException e) {
      throw new IllegalArgumentException("秘钥错误");
    }
  }

}