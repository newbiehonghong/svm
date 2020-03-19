package svm.security.util;

import org.apache.commons.codec.binary.Base64;

public class DESCipherTest {
    public static void main(String[] args) {
        String text = "neusoft";
        try {
//            byte[] bs = text.getBytes("UTF-8");
//            for(byte b : bs) {
//                String s = Integer.toHexString(b);
//                System.out.print("0x" + s + ", ");
//            }
            long start = System.currentTimeMillis();


            for( int i = 0; i < 1; i++) {
                String s1 = TripleDESCrypto.encrypt(text);
                System.out.println(s1);
                String s2 = TripleDESCrypto.decrypt(s1);
                System.out.println(s2);
            }

//            for( int i = 0; i < 1; i++) {
//                String s1 = DESCipher.encodeBase64(text);
//                //System.out.println(s1);
//                String s2 = DESCipher.decodeBase64(s1);
//                //System.out.println(s2);
//            }

            for( int i = 0; i < 1; i++) {
                String s1 = new String(Base64.encodeBase64(text.getBytes()));

                System.out.println(s1);
                String s2 = new String(Base64.decodeBase64(s1.getBytes()));
                System.out.println(s2);
            }

            System.out.println(System.currentTimeMillis() - start);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
