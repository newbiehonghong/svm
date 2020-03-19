package svm.common.crypto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.Charset;

public class DESUtils {
    private final static String DEFAULT_KEY = "12bc56mn";

    private final static String CHARSET = "UTF-8";

    private final static String ALGORITHM = "DES";

    private final static String CIPHER = "DES/CBC/PKCS5Padding";

    public static String decode(String text) {
        return decrypt(text, Charset.forName(CHARSET), DEFAULT_KEY);
    }

    public static String encode(String text) {
        return encrypt(text, Charset.forName(CHARSET), DEFAULT_KEY);
    }

    public static String decrypt(String hexStr, Charset charset, String sKey) {
        byte[] src = parseHexStr2Byte(hexStr);
        byte[] buf = decrypt(src, sKey);
        return new String(buf, charset);
    }

    public static String encrypt(String srcStr, Charset charset, String sKey) {
        byte[] src = srcStr.getBytes(charset);
        byte[] buf = encrypt(src, sKey);
        return parseByte2HexStr(buf);
    }

    public static byte[] encrypt(byte[] data, String sKey) {
        try {
            byte[] key = sKey.getBytes();

            IvParameterSpec iv = new IvParameterSpec(key);
            DESKeySpec desKey = new DESKeySpec(key);

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            SecretKey securekey = keyFactory.generateSecret(desKey);

            Cipher cipher = Cipher.getInstance(CIPHER);

            cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);

            return cipher.doFinal(data);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] decrypt(byte[] src, String sKey) {
        try {
            byte[] key = sKey.getBytes();
            // 初始化向量
            IvParameterSpec iv = new IvParameterSpec(key);
            // 创建一个DESKeySpec对象
            DESKeySpec desKey = new DESKeySpec(key);
            // 创建一个密匙工厂
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            // 将DESKeySpec对象转换成SecretKey对象
            SecretKey securekey = keyFactory.generateSecret(desKey);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance(CIPHER);
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
            // 真正开始解密操作
            return cipher.doFinal(src);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
