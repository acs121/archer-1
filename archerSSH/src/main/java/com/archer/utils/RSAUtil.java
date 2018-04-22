/** 
 * Project Name:archerSSH 
 * File Name:RSAUtil.java 
 * Package Name:com.archer.utils 
 * Date:2018年4月21日上午10:21:41 
 * Copyright (c) 2018, 1255903774@qq.com All Rights Reserved. 
 * 
 */  

package com.archer.utils;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

/**
 * @author Kunjin Guo
 */
public class RSAUtil {
	private static String priK="MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAKqdZKZH5RubNsCDY7YMDd+YN2dct3mN2bibx4xwdCt7ZQgXTOb+ZOv4RIzW99Ef4XERCl4c34vdesWRQYd6mSg8RhXuzlwlmCCdv9/ly+WhN7bETt/+3/epGQyjyA7CqZAiKv+oCe1brgf+qMnM/q3IFT82iEMLkBRWZ6JQzdtzAgMBAAECgYEAkOYpM9s9Zeu1XVx5E4Q7SBkzfJJOtGpXbSTIHarnzoH2tR9szYViWtG4DlEHmNDVmzwYrQMii9umyCHsIE5rwUxzFPfFWWOjd1AjYGWAJ4sz/MXvwab3X1UmU8LKF/DhIrf0XJ37QNiMZ16ApvuIGvdGKlE5MbS6bR25vVxxcikCQQDxfzlc57poQsOZ6RbHGV97t4NLkE/jkBjqI0oKBSLhLz8EVytd+Y7ODmzKG7nZdDOU9KPQOu8qs8ptTfLv4RVVAkEAtNxtKuw0oJG5/783NT3DNPrkIIw6hF4annZCdbYyLb2eHq69yrl8nuJNlevFi6AeuERCVsWiqsQT97j8c1UtpwJBAOZyKwD2t7CKFTEnde7jzXO3OHjA6xsSBFRY/Gv3XsNXXGD99YolfBoxNarfLkfNVPONR0yLEHLFQiNvwKUbOkkCQQCyrk8gJ8p6lF5JH7XAYOt5bSFyUngBjCMKSNWyC7WZYQtVTtfs78ICCpwSMKOzJRcUf0N3DLx/r0X26IhovU9jAkBKTryz1jCxfJv6ZCZ24GSt6VrACUjA+pfgflBdlq7sfrbcjeGU3kzFFUAJz9svYAs1Gwj5OKVBIFvF9n8a0/6m";
	private static PrivateKey privateKey;
	static {
		try {
			privateKey=getPrivateKey(priK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    //生成密钥对  
    public static KeyPair genKeyPair(int keyLength) throws Exception{  
        KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");  
        keyPairGenerator.initialize(1024);        
        return keyPairGenerator.generateKeyPair();  
    }  
    //公钥加密  
    private static byte[] encrypt(byte[] content, PublicKey publicKey) throws Exception{  
        Cipher cipher=Cipher.getInstance("RSA");//java默认"RSA"="RSA/ECB/PKCS1Padding"  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
        return cipher.doFinal(content);  
    }  
      
    //私钥解密  
    private static byte[] decrypt(byte[] content, PrivateKey privateKey) throws Exception{  
        Cipher cipher=Cipher.getInstance("RSA");  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
        return cipher.doFinal(content);  
    }  
    
    
  //将base64编码后的公钥字符串转成PublicKey实例  
    private static PublicKey getPublicKey(String publicKey) throws Exception{  
        byte[ ] keyBytes=Base64.getDecoder().decode(publicKey.getBytes());  
        X509EncodedKeySpec keySpec=new X509EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");  
        return keyFactory.generatePublic(keySpec);    
    }  
      
    //将base64编码后的私钥字符串转成PrivateKey实例  
    private static PrivateKey getPrivateKey(String privateKey) throws Exception{  
        byte[ ] keyBytes=Base64.getDecoder().decode(privateKey.getBytes());  
        PKCS8EncodedKeySpec keySpec=new PKCS8EncodedKeySpec(keyBytes);  
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");  
        return keyFactory.generatePrivate(keySpec);  
    }  
    public static String base64DecryptString(String base64) throws Exception {
    	return new String(decrypt(Base64.getDecoder().decode(base64), privateKey));
    }
      
}
