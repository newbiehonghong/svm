package svm.common;

import svm.common.crypto.DESUtils;

public class DESTest {
    public static void main(String[] args) {
        String s = "postgres";
        String d = DESUtils.encode(s);
        System.out.println(d);
        System.out.println(DESUtils.decode(d));
    }
}
