package svm.common.exception;

import java.text.MessageFormat;

public class BusinessException extends RuntimeException {

    private int code;

    private String message;

    public BusinessException(Throwable cause) {
        this(cause, 500, null);
    }

    public BusinessException(Throwable cause, int code) {
        this(cause, code, null);
    }

    public BusinessException(String message, Object... objects) {
        this(null, 500, message, objects);
    }

    public BusinessException(int code, String message, Object... objects) {
        this(null, code, message, objects);
    }

    public BusinessException(Throwable cause, int code, String message, Object... objects) {
        super(cause);

        this.code = code;
        if (message == null) {
            this.message = cause == null ? null : cause.getMessage();
        } else {
            this.message = MessageFormat.format(message, objects);
        }
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[message]=").append(message == null ? "{null}" : message).append("\n");
        sb.append("[code]=").append(code).append("\n");
        sb.append("[class]").append(getClass().getName());

        return sb.toString();
    }
}
