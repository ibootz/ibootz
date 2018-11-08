package top.bootz.commons.helper;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import top.bootz.commons.exception.BaseRuntimeException;

public final class AesHelper {

    private static final String ALGORITHM = "AES";

    private static final String ALGORITHM_MODE = "AES/GCM/NoPadding";

    private static final int TAG_LENGTH_BIT = 128;

    private static final int IV_LENGTH_BYTE = 12;

    private static final String SEED_1 = "1HlQ19fuZsmoH7YexZmGjhRNJFmyCbHhAIauH9N3Pv8=";

    private static final String SEED_2 = "97rQgHn9cUX2mthoUpmtLuZi/coKnCu+0rkqoPhySpg=";

    private AesHelper() {
    }

    /**
     * @param byte[] source 要被加密的字节数组
     * @return byte[] 加密后的字节数组
     */
    public static byte[] encrypt(byte[] source) {
        try {
            byte[] iv = getIv();
            final Cipher cipher = Cipher.getInstance(ALGORITHM_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, buildSecretKey(), new GCMParameterSpec(TAG_LENGTH_BIT, iv));
            byte[] encrypted = cipher.doFinal(source);

            ByteBuffer byteBuffer = ByteBuffer.allocate(1 + iv.length + encrypted.length);
            byteBuffer.put((byte) iv.length);
            byteBuffer.put(iv);
            byteBuffer.put(encrypted);
            return byteBuffer.array();
        } catch (Exception e) {
            throw new BaseRuntimeException("Failed to encrypt the content!", e);
        }
    }

    /**
     * @param String source 要被加密的字符串
     * @return String 加密后经过base64编码的字符串
     */
    public static String encryptToBase64(String source) {
        return CodecHelper.toBase64(AesHelper.encrypt(source.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * @param byte[] encryptedContent 要被解密的字节数组
     * @return String 解密后的字符串
     */
    public static byte[] decrypt(byte[] encryptedData) {
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(encryptedData);
            int ivLength = byteBuffer.get();
            byte[] iv = new byte[ivLength];
            byteBuffer.get(iv);
            byte[] encrypted = new byte[byteBuffer.remaining()];
            byteBuffer.get(encrypted);

            Cipher cipher = Cipher.getInstance(ALGORITHM_MODE);
            cipher.init(Cipher.DECRYPT_MODE, buildSecretKey(), new GCMParameterSpec(TAG_LENGTH_BIT, iv));
            return cipher.doFinal(encrypted);
        } catch (Exception e) {
            throw new BaseRuntimeException("Failed to decrypt the encrypted content!", e);
        }
    }

    /**
     * @param byte[] base64Encrypted 要被解密的经过base64编码过的内容
     * @return String 解密后的字符串
     */
    public static String decryptFromBase64(String base64Encrypted) {
        String result = StringHelper.EMPTY;
        try {
            result = new String(decrypt(CodecHelper.fromBase64(base64Encrypted)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new BaseRuntimeException("Failed to decrypt the encrypted base64 content!", e);
        }
        return result;
    }

    private static byte[] getIv() throws NoSuchAlgorithmException {
        byte[] iv = new byte[IV_LENGTH_BYTE];
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(SEED_1.getBytes(StandardCharsets.UTF_8));
        random.nextBytes(iv);
        return iv;
    }

    private static SecretKey buildSecretKey() throws NoSuchAlgorithmException {
        // 构造密钥生成器，指定为AES算法,不区分大小写
        KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM);

        // 根据ecnodeRules规则初始化密钥生成器，生成一个128位的随机源,根据传入的字节数组
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(SEED_2.getBytes(StandardCharsets.UTF_8));
        keygen.init(128, random);

        // 产生原始对称密钥
        SecretKey originalKey = keygen.generateKey();

        // 获得原始对称密钥的字节数组
        byte[] raw = originalKey.getEncoded();

        // 根据字节数组生成AES密钥
        return new SecretKeySpec(raw, ALGORITHM);
    }

}