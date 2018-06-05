package top.bootz.commons.helper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class Md5Helper {

	private static final char[] md5String = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
			'F' };

	private Md5Helper() {
	}

	public final static String md5(String content) {
		try {
			byte[] btInput = content.getBytes(StandardCharsets.UTF_8);
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			int k = 0;
			char[] str = new char[j * 2];
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = md5String[byte0 >>> 4 & 0xf];
				str[k++] = md5String[byte0 & 0xf];
			}

			// 返回经过加密后的字符串
			return new String(str);
		} catch (Exception e) {
			log.error("MD5 encode failed!", e);
			return null;
		}
	}

}
