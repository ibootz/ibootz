package top.bootz.commons.helper;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import top.bootz.commons.constant.SecurityConstants;

/**
 * RSA公钥/私钥/签名工具包
 * <p>
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 * </p>
 * 
 * @author John
 *
 */
public class RsaHelper {

	private static final Logger LOGGER = LogManager.getLogger(RsaHelper.class);

	/**
	 * 加密算法
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/**
	 * 签名算法
	 */
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	/**
	 * 公钥文件存储路径
	 */
	private static final String PUBLIC_KEY_FILE_PATH = getBaseClassPath() + SecurityConstants.PUBLIC_KEY_FILE_PATH;

	/**
	 * 私钥文件存储路径
	 */
	private static final String PRIVATE_KEY_FILE_PATH = getBaseClassPath() + SecurityConstants.PRIVATE_KEY_FILE_PATH;

	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	private static final Map<String, Object> keyMap = new HashMap<>(2);

	private static final int KEY_SIZE = 1024;

	private static final String PUBLIC_KEY = "RSAPublicKey";

	private static final String PRIVATE_KEY = "RSAPrivateKey";

	private static KeyFactory keyFactory;

	/** 随JVM启动自动加载系统中的公钥私钥到内存中（分布式系统中，保持密钥文件的一致） */
	static {
		try {
			keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			initKey();
			LOGGER.trace("RSA Public Key [" + getBase64PublicKey() + "] Private Key [" + getBase64PrivateKey() + "]");
		} catch (Exception e) {
			keyMap.clear();
			LOGGER.error("Init RSA key failure!", e);
		}
	}

	private RsaHelper() {
	}

	/**
	 * 获取经过base64编码的公钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getBase64PublicKey() {
		Key key = (Key) keyMap.get(PUBLIC_KEY);
		if (key != null) {
			return CodecHelper.toBase64(key.getEncoded());
		}
		return null;
	}

	/**
	 * 获取经过base64编码的私钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getBase64PrivateKey() {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		if (key != null) {
			return CodecHelper.toBase64(key.getEncoded());
		}
		return null;
	}

	/**
	 * 获取RSAPublicKey公钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static PublicKey getPublicKey() throws Exception {
		return (PublicKey) keyMap.get(PUBLIC_KEY);
	}

	/**
	 * 获取RSAPrivateKey私钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey() throws Exception {
		return (PrivateKey) keyMap.get(PRIVATE_KEY);
	}

	/**
	 * 从base64编码的公钥key字符串中得到PublicKey对象
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PublicKey getPublicKeyFromBase64(String base64PublicKey) throws Exception {
		return keyFactory.generatePublic(new X509EncodedKeySpec(CodecHelper.fromBase64(base64PublicKey)));
	}

	/**
	 * 从base64编码的私钥key字符串中得到PrivateKey对象
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKeyFromBase64(String base64PrivateKey) throws Exception {
		return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(CodecHelper.fromBase64(base64PrivateKey)));
	}

	/**
	 * 从外部文件中加载密钥文件，转换为对象之后保存到keyMap中
	 * 
	 * @throws Exception
	 */
	public static void initKey() throws Exception {
		String base64PublicKey = readFromFile(PUBLIC_KEY_FILE_PATH);
		String base64PrivateKey = readFromFile(PRIVATE_KEY_FILE_PATH);
		if (StringUtils.isNoneBlank(base64PublicKey) && StringUtils.isNotBlank(base64PrivateKey)) {
			keyMap.put(PUBLIC_KEY, getPublicKeyFromBase64(base64PublicKey));
			keyMap.put(PRIVATE_KEY, getPrivateKeyFromBase64(base64PrivateKey));
		}
	}

	/**
	 * 公钥加密数据
	 * <p>
	 * RSA加密明文最大长度117字节,所以此处采用分段加密
	 * </p>
	 * 
	 * @param source
	 *            源数据
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] source) throws Exception {
		Key publicKey = getPublicKey();
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		int inputLen = source.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 分段加密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(source, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(source, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptedData = out.toByteArray();
		out.close();
		return encryptedData;
	}

	/**
	 * 私钥解密数据
	 * <p>
	 * 解密要求密文最大长度为128字节,所以此处采用分段解密
	 * </p>
	 * 
	 * @param cryptograph
	 *            密文
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] encryptedData) throws Exception {
		Key privateKey = getPrivateKey();
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	private static String readFromFile(String filePath) {
		try (FileReader fr = new FileReader(filePath); BufferedReader br = new BufferedReader(fr);) {
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
			}
			return sb.toString();
		} catch (FileNotFoundException e) {
			LOGGER.info("没有找到密钥文件！ [" + filePath + "]");
		} catch (IOException e) {
			LOGGER.info("读取密钥文件失败！ [" + filePath + "]");
		}
		return null;
	}

	public static String getBaseClassPath() {
		return Thread.currentThread().getClass().getResource("/").getPath();
	}

	/**
	 * 生成密钥对,并保存在文件中，用于定期更换
	 * 
	 * @throws Exception
	 */
	public static void genKeyPair(String privateKeyPath, String publicKeyPath) throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(KEY_SIZE);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		String publicKeyStr = CodecHelper.toBase64(publicKey.getEncoded());
		String privateKeyStr = CodecHelper.toBase64(privateKey.getEncoded());
		// 将密钥对写入到文件
		writeToFile(privateKeyStr, privateKeyPath);
		writeToFile(publicKeyStr, publicKeyPath);
	}

	private static void writeToFile(String publicKeyStr, String filePath) throws IOException {
		FileUtils.write(new File(filePath), publicKeyStr, StandardCharsets.UTF_8);
	}

	/*public static void main(String[] args) {
		try {
			String privateKeyPath = "I:/git_repository/orion/orion-pms/src/main/resources/keystore/privateKey.keystore";
			String publicKeyPath = "I:/git_repository/orion/orion-pms/src/main/resources/keystore/publicKey.keystore";
			genKeyPair(privateKeyPath, publicKeyPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

}
