package svm.common.exception;

public class BaseRuntimeException extends RuntimeException {
    public BaseRuntimeException(String message) {
        super(message);
    }

    public BaseRuntimeException(Throwable cause) {
        super(cause);
    }
}
