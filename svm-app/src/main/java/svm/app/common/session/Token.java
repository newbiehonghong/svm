package svm.app.common.session;

import org.apache.commons.codec.binary.Base64;

public class Token {
    private static final String SPLIT_SIGN = ",";

    private long userId;

    private long lastAccessTime;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(long lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    /**
     * DES或者3DES加密解密每次要消耗300多毫秒，所以不对token进行加密<br>
     * 可以使用https或者给lastAccessTime串里边固定位置插入固定长度的随机数字
     */
    public static Token parse(String text) {
        if (text == null) {
            throw new RuntimeException("Invalid token");
        }

        String s = new String(Base64.decodeBase64(text.getBytes()));
        if (!s.contains(SPLIT_SIGN)) {
            throw new RuntimeException("Invalid token");
        }

        String[] ss = s.split(SPLIT_SIGN);
        if (ss.length != 2) {
            throw new RuntimeException("Invalid token");
        }

        Token token = new Token();
        token.setUserId(Long.parseLong(ss[0]));
        token.setLastAccessTime(Long.parseLong(ss[1]));
        return token;
    }

    public static Token refresh(Token oldToken) {
        Token token = new Token();
        token.setUserId(oldToken.userId);
        token.setLastAccessTime(System.currentTimeMillis());

        return token;
    }

    @Override
    public String toString() {
        String text = userId + SPLIT_SIGN + lastAccessTime;
        return new String(Base64.encodeBase64(text.getBytes()));
    }
}
