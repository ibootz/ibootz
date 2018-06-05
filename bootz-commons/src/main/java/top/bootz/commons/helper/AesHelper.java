package top.bootz.commons.helper;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import top.bootz.commons.constant.AppConstants;

public final class AesHelper {

	private static final Logger LOGGER = LogManager.getLogger(AesHelper.class);

	private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

	private static final String SECRETKEY = "bjCUctiRaE1J1zJ0hEdwjrM82WKEmcm7SYhg=";

	private static SecretKeySpec keySpec = null;

	private AesHelper() {
	}

	static {
		try {
			keySpec = shaSecretKey();
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			LOGGER.error("init keySpec failed!", e);
		}
	}

	/**
	 * 使用SHA-256加密AES密钥
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	private static SecretKeySpec shaSecretKey() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] data = md.digest(SECRETKEY.getBytes(AppConstants.CHARSET_UTF_8));
		return new SecretKeySpec(data, "AES");
	}

	/**
	 * @param byte[]
	 *            source 要被加密的字节数组
	 * @return byte[] 加密后的字节数组
	 */
	public static byte[] encrypt(byte[] source) {
		byte[] result = null;
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			result = cipher.doFinal(source);
		} catch (Exception e) {
			LOGGER.error("AES encode failed!", e);
		}
		return result;
	}

	/**
	 * @param String
	 *            source 要被加密的字符串
	 * @return String 加密后经过base64编码的字符串
	 */
	public static String encryptToBase64(String source) {
		String result = null;
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			result = CodecHelper.toBase64(cipher.doFinal(source.getBytes(AppConstants.CHARSET_UTF_8)));
		} catch (Exception e) {
			LOGGER.error("AES encode failed!", e);
		}
		return result;
	}

	/**
	 * @param byte[]
	 *            encryptedContent 要被解密的字节数组
	 * @return String 解密后的字符串
	 */
	public static byte[] decrypt(byte[] encryptedContent) {
		byte[] result = null;
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			result = cipher.doFinal(encryptedContent);
		} catch (Exception e) {
			LOGGER.error("AES decode failed!", e);
		}
		return result;
	}

	/**
	 * @param byte[]
	 *            base64Encrypted 要被解密的经过base64编码过的内容
	 * @return String 解密后的字符串
	 */
	public static String decryptFromBase64(String base64Encrypted) {
		String result = StringHelper.EMPTY;
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			result = new String(cipher.doFinal(CodecHelper.fromBase64(base64Encrypted)), AppConstants.CHARSET_UTF_8);
		} catch (Exception e) {
			LOGGER.error("AES decode failed!", e);
		}
		return result;
	}

	public static void main(String[] args) {
		String source = "中文English*&&$uawhuh2138761872akwhukh21379287dawkuhku2h1312983717dasjHLIJl213028^%$#（*781=2dawkuhadwadwduukw&*(*(7";
		String after = encryptToBase64(source);
		String before = decryptFromBase64(after);
		System.out.println(after);
		System.out.println(source);
		System.out.println(before);
	}
}