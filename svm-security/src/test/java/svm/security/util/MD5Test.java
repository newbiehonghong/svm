package svm.security.util;

public class MD5Test {
    public static void main(String[] args) {
        String s = "a";
        try {
            long start = System.currentTimeMillis();

            for( int i = 0; i < 1; i++) {
                String ss = MD5Digest.digest(s);
                System.out.println(ss);
            }

            System.out.println(System.currentTimeMillis() - start);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
