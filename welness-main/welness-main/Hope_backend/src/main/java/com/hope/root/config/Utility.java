package com.hope.root.config;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Utility {
	public static Date stringToDateWithFormat(String dateString, String format) {

		try {
		DateFormat dateformat = new SimpleDateFormat(format);
		Date date;
		date = dateformat.parse(dateString);
		return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; 
	}
	
	public static String dateToStringWithFormat(Date date, String format) {

		DateFormat dateformat = new SimpleDateFormat(format);
		String dateString;
		dateString = dateformat.format(date);
		return dateString;
		
	}
	
	public static String DatePlusUUID() {
		Date date=new Date();
		UUID uid = UUID.randomUUID();
		String str = "" + uid;
		int uidhas = str.hashCode();
		String filterStr = "" + uidhas;
		str = filterStr.replaceAll("-", "");
		String Id = String.valueOf(str)
				.substring(0, 5);
		String dateStr = null;
		String datePlusUUID = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
		dateStr = sdf.format(date);
		datePlusUUID = dateStr + Id;
		return datePlusUUID;
	}
	
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
	  
}
