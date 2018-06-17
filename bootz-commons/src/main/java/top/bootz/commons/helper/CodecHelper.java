package top.bootz.commons.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Base64;

/**
 * 编解码相关的工具方法
 * 
 * @author John
 *
 */
public final class CodecHelper {

	/**
	 * 文件读取缓冲区大小
	 */
	private static final int CACHE_SIZE = 1024;

	private CodecHelper() {
	}

	public static String toUrlSafeBase64(byte[] content) {
		return Base64.getUrlEncoder().encodeToString(content);
	}

	public static byte[] fromUrlSafeBase64(String base64Content) {
		return Base64.getUrlDecoder().decode(base64Content);
	}

	public static String toBase64(byte[] content) {
		return Base64.getEncoder().encodeToString(content);
	}

	public static byte[] fromBase64(String base64Content) {
		return Base64.getDecoder().decode(base64Content);
	}

	/**
	 * <p>
	 * 将文件编码为BASE64字符串
	 * </p>
	 * <p>
	 * 大文件慎用，可能会导致内存溢出
	 * </p>
	 * 
	 * @param filePath
	 *            文件绝对路径
	 * @return
	 * @throws Exception
	 */
	public static String encodeFile(String filePath) throws Exception {
		byte[] bytes = fileToByte(filePath);
		return toUrlSafeBase64(bytes);
	}

	/**
	 * <p>
	 * BASE64字符串转回文件
	 * </p>
	 * 
	 * @param filePath
	 *            文件绝对路径
	 * @param base64
	 *            编码字符串
	 * @throws Exception
	 */
	public static void decodeToFile(String filePath, String base64) throws Exception {
		byte[] bytes = fromUrlSafeBase64(base64);
		byteArrayToFile(bytes, filePath);
	}

	/**
	 * <p>
	 * 文件转换为二进制数组
	 * </p>
	 * 
	 * @param filePath
	 *            文件路径
	 * @return
	 * @throws Exception
	 */
	public static byte[] fileToByte(String filePath) throws Exception {
		byte[] data = new byte[0];
		File file = new File(filePath);
		if (!file.exists()) {
			return data;
		}
		try (FileInputStream in = new FileInputStream(file);
				ByteArrayOutputStream out = new ByteArrayOutputStream(2048)) {
			byte[] cache = new byte[CACHE_SIZE];
			int nRead = 0;
			while ((nRead = in.read(cache)) != -1) {
				out.write(cache, 0, nRead);
				out.flush();
			}
			data = out.toByteArray();
		}
		return data;
	}

	/**
	 * <p>
	 * 二进制数据写文件
	 * </p>
	 * 
	 * @param bytes
	 *            二进制数据
	 * @param filePath
	 *            文件生成目录
	 */
	public static void byteArrayToFile(byte[] bytes, String filePath) throws Exception {
		OutputStream out = null;
		try (InputStream in = new ByteArrayInputStream(bytes);) {
			File destFile = new File(filePath);
			if (!destFile.getParentFile().exists()) {
				destFile.getParentFile().mkdirs();
			}

			if (!destFile.createNewFile()) {
				return;
			}
			
			out = new FileOutputStream(destFile);
			byte[] cache = new byte[CACHE_SIZE];
			int nRead = 0;
			while ((nRead = in.read(cache)) != -1) {
				out.write(cache, 0, nRead);
				out.flush();
			}
		} finally {
			if (out != null) {
				out.close();
			}
		}

	}

}
