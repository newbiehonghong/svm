package svm.security.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.Key;

public class TripleDESCrypto {
    private static final String CHARSET = "UTF-8";

    private static final byte[] KEY = { 0x53, 0x68, 0x6f, 0x77, 0x4d, 0x65, 0x54, 0x68, 0x65, 0x4d, 0x6f, 0x6e, 0x65, 0x79, 0x32, 0x30, 0x31, 0x38, 0x32, 0x30, 0x30, 0x38, 0x31, 0x31, 0x32, 0x32, 0x31, 0x36 };

    private static final String ALGORITHM = "DESede";//即3DES

    public static String encrypt(String text) {
        try {
            byte[] bytes = text.getBytes(CHARSET);
            return new String(Base64.encodeBase64(encryptByte(bytes)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String text) {
        try {
            byte[] bytes = Base64.decodeBase64(text.getBytes());
            return new String(decryptByte(bytes), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] encryptByte(byte[] bytes) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            Key key = keyFactory.generateSecret(new DESedeKeySpec(KEY));
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] decryptByte(byte[] bytes) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            Key key = keyFactory.generateSecret(new DESedeKeySpec(KEY));
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
