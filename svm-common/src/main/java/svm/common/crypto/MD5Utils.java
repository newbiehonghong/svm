package svm.common.crypto;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;

public class MD5Utils {
    private static final String ALGORITHM = "MD5";

    private static final String ENCODING = "UTF-8";

    public static String digest(String input) {
        try {
            return Hex.encodeHexString(digest(input.getBytes(ENCODING)));
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    public static byte[] digest(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            return md.digest(input);
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }
}
