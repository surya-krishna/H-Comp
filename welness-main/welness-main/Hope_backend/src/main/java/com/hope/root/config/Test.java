package com.hope.root.config;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/*public class Test {
	
	public static SecretKey generateKey()
		    throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		    String password = "asgdfgha";
		    String salt = "ddf";
		    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		    KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 10, 256);
		    SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
		        .getEncoded(), "AES");
		    return secret;
		
	}
	
	public static IvParameterSpec generateIv() {
	    byte[] iv = new byte[16];
	    new SecureRandom().nextBytes(iv);
	    return new IvParameterSpec(iv);
	}
	
	public static String encrypt(String input){
		try {    
		SecretKey key = generateKey();
		    IvParameterSpec ivParameterSpec = generateIv();
		    String algorithm = "AES/CBC/PKCS5Padding";
		    Cipher cipher = Cipher.getInstance(algorithm);
		    cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
		    byte[] cipherText = cipher.doFinal(input.getBytes());
		    return Base64.getEncoder()
		        .encodeToString(cipherText);
	}
	catch(Exception e) {
		e.printStackTrace();
		return null;
	}
		}
	
	public static String decrypt(String cipherText){
		try {	
			SecretKey key = generateKey();
		    IvParameterSpec ivParameterSpec = generateIv();
		    String algorithm = "AES/CBC/PKCS5Padding";
		    Cipher cipher = Cipher.getInstance(algorithm);
		    cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
		    byte[] plainText = cipher.doFinal(Base64.getDecoder()
		        .decode(cipherText));
		    return new String(plainText);
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		}
	
	public static void main(String args[]) {
		System.out.println(encrypt("hello"));
	}
}*/



public class Test {

  private static SecretKeySpec secretKey;
  private static byte[] key;


  public static void setKey() {
    MessageDigest sha = null;
    String myKey="hfcxhchgchgchgchgchcg";
    try {
      key = myKey.getBytes("UTF-8");
      sha = MessageDigest.getInstance("SHA-1");
      key = sha.digest(key);
      key = Arrays.copyOf(key, 16);
      secretKey = new SecretKeySpec(key, "AES");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static String encrypt(final String strToEncrypt) {
    try {
      setKey();
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
      return Base64.getEncoder()
        .encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
    } catch (Exception e) {
      System.out.println("Error while encrypting: " + e.toString());
    }
    return null;
  }

  public static String decrypt(final String strToDecrypt) {
    try {
      setKey();
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
      cipher.init(Cipher.DECRYPT_MODE, secretKey);
      return new String(cipher.doFinal(Base64.getDecoder()
        .decode(strToDecrypt)));
    } catch (Exception e) {
      System.out.println("Error while decrypting: " + e.toString());
    }
    return null;
  }
  
  public static void main(String args[]) {
	  final String secretKey = "hfcxhchgchgchgchgchcg";

	  String originalString = "18e6704a-8cb0-439a-89ec-0ddaeb1afbcc";
	  String encryptedString = encrypt(originalString) ;
	  String decryptedString = decrypt("odz8V8E8QMhCH8IOrBHzV/n7W253TtyMW7+4njKkKX5oJ2wGe+/wkK7lRZHuZ0GYr3Ff/6shIhkoXzt1JEJAKDwV4XPToP2IsO1YiMbpiAzAYJ8q2jnol/XNu/05EwhR8bptmt6WPhoIlLIU04YH8J7W14mU2XJSv9zb35wE+CI/ypGOxSTuLIWzowM4MDzNkY2iLsnf+ft+2LTOxwOdeuMv3CIkVuhRoXBX99C2WaOYCsSK9xevgJJ0x5hfjwOjXEhMekVXhI5uHXyWdcZ6enhjDpjJ8Wv9ivHHZ8/n9WfH4FQJodN+jb2ss/RzUmfJvf3kpT1lGgEVoSsjcy4O70FsyKsfMxC5pyny1rKBOpk=") ;

	  System.out.println(originalString);
	  System.out.println(encryptedString);
	  System.out.println(decryptedString);
}
}