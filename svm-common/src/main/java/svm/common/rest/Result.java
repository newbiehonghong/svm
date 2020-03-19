package svm.common.rest;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import svm.common.exception.BusinessException;

import java.io.Serializable;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

    private static final int STATUS_SUCCESS = 200;

    private T payload;

    private int status;

    private String message;

    private String error;

    public Result(T payload) {
        this(payload, STATUS_SUCCESS, null);
    }

    public Result(int status, String message) {
        this(null, status, message);
    }

    public Result(int status, String message, String error) {
        this(null, status, message, error);
    }

    public Result(T payload, int status, String message) {
        this(payload, status, message, null);
    }

    public Result(T payload, int status, String message, String error) {
        this.payload = payload;
        this.status = status;
        this.message = message;
        this.error = error;
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public boolean isSuccessful() {
        return status == STATUS_SUCCESS;
    }

    @JsonIgnore
    @JSONField(serialize = false)
    public T get() {
        if (isSuccessful()) {
            return payload;
        } else {
            throw new BusinessException(status, message);
        }
    }
}
