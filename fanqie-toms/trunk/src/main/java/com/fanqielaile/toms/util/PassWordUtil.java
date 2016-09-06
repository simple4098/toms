package com.fanqielaile.toms.util;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class PassWordUtil {
	
	//哈希加密算法，传入要加密的字符串得到加密后的字符串
	public static String getShaPwd(String pwd) {
		return getShaPwd(pwd, null);
	}
	
	//哈希加密算法，传入要加密的字符串以及需要盐值，默认是用户名
	public static String getShaPwd(String pwd, String userName) {
		ShaPasswordEncoder sha = new ShaPasswordEncoder();
		sha.setEncodeHashAsBase64(false);
		pwd = sha.encodePassword(pwd, userName);
		return pwd;
	}
	
	//md5加密算法，传入要加密的字符串得到加密后的字符串
	public static String getMd5Pwd(String pwd) {
		return getMd5Pwd(pwd, null);
	}
		
	//md5加密算法，传入要加密的字符串以及需要的盐值，默认是用户名
	public static String getMd5Pwd(String pwd, String userName) {
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		md5.setEncodeHashAsBase64(false);
		pwd = md5.encodePassword(pwd, userName);
		return pwd;
	}

	public static String getMd5(String txt) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(txt.getBytes("utf-8"));
			StringBuffer buf = new StringBuffer();
			for (byte b : md.digest()) {
				buf.append(String.format("%02x", b & 0xff));
			}
			return buf.toString();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 根据输入字符串生成公钥与私钥
	 */
	public static Map<String,byte[]> generater(String s) {
		Map<String,byte[]> map = new HashMap<String,byte[]>();
		try {
			java.security.KeyPairGenerator keygen = java.security.KeyPairGenerator.getInstance("RSA");
			SecureRandom secrand = new SecureRandom();
			secrand.setSeed(s.getBytes()); // 初始化随机产生器
			keygen.initialize(1024, secrand);
			KeyPair keys = keygen.genKeyPair();
			PublicKey pubkey = keys.getPublic();
			PrivateKey prikey = keys.getPrivate();
			map.put("pubKey", Base64.encodeBase64(pubkey.getEncoded()));
			map.put("priKey", Base64.encodeBase64(prikey.getEncoded()));
		} catch (java.lang.Exception e) {
			System.out.println("生成密钥对失败");
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * Description:数字签名
	 * 
	 * @param priKeyText
	 * @param plainText
	 * @return
	 */
	public static byte[] sign(byte[] priKeyText, String plainText) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(priKeyText));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey prikey = keyf.generatePrivate(priPKCS8);
			// 用私钥对信息生成数字签名
			java.security.Signature signet = java.security.Signature.getInstance("MD5withRSA");
			signet.initSign(prikey);
			signet.update(plainText.getBytes());
			byte[] signed = Base64.encodeBase64(signet.sign());
			return signed;
		} catch (java.lang.Exception e) {
			System.out.println("签名失败");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * Description:校验数字签名,此方法不会抛出任务异常,成功返回true,失败返回false,要求全部参数不能为空
	 * 
	 * @param pubKeyText 公钥,base64编码
	 * @param plainText 明文
	 * @param signText 数字签名的密文,base64编码
	 * @return 校验成功返回true 失败返回false
	 */
	public static boolean verify(byte[] pubKeyText, String plainText, byte[] signText) {
		try {
			// 解密由base64编码的公钥,并构造X509EncodedKeySpec对象
			java.security.spec.X509EncodedKeySpec bobPubKeySpec = new java.security.spec.X509EncodedKeySpec(Base64.decodeBase64(pubKeyText));
			// RSA对称加密算法
			java.security.KeyFactory keyFactory = java.security.KeyFactory.getInstance("RSA");
			// 取公钥匙对象
			java.security.PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);
			// 解密由base64编码的数字签名
			byte[] signed = Base64.decodeBase64(signText);
			java.security.Signature signatureChecker = java.security.Signature.getInstance("MD5withRSA");
			signatureChecker.initVerify(pubKey);
			signatureChecker.update(plainText.getBytes());
			// 验证签名是否正常
			if (signatureChecker.verify(signed))
				return true;
			else
				return false;
		} catch (Throwable e) {
			System.out.println("校验签名失败");
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * MD5加密
	 * 
	 * @param input
	 *            要加密的字符串
	 * @return
	 */
	public static String encryptByMD5(String input) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(input.getBytes());

			byte byteData[] = md.digest();

			// 二进制转换为十六进制
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}

			return sb.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * SHA256加密
	 * 
	 * @param input
	 *            要加密的字符串
	 * @return
	 */
	public static String encryptBySHA(String input) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(input.getBytes());

			byte byteData[] = md.digest();

			// 二进制转换为十六进制
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}

			return sb.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 密码验证
	 * 
	 * @param encryptPass 原密码，加过密
	 * @param salt 管理员密码Salt
	 * @param currPass 当前密码
	 * @return 相同返回true，否则false
	 * @author hai
	 * @date 2014年2月26日下午4:22:11
	 */
	public static boolean verifyPassword(String encryptPass, String salt, String currPass) {
		if (null == currPass || currPass.trim().length() == 0) {
			return false;
		}
		return encryptPass.equals(encryptBySHA(currPass + salt));
	}

	public static void main(String[] arg){
//		System.out.println(getMd5Pwd("000000"));
		System.out.println(getShaPwd("000000","root"));
		System.out.println("-pubKey:"+new String(generater("wanda").get("pubKey")));
		System.out.println("-priKey:"+new String(generater("wanda").get("priKey")));
		System.out.println("--"+new String(sign(generater("wanda").get("priKey"),"mowei")));
		System.out.println(verify(generater("wanda").get("pubKey"),"mowei",sign(generater("wanda").get("priKey"),"mowei")));
	}
	
}
