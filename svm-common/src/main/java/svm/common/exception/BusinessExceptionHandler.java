package svm.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import svm.common.rest.Result;

@ControllerAdvice
public class BusinessExceptionHandler {
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public Result handleException(BusinessException ex) {

        return new Result<>(ex.getCode(), ex.getMessage());
    }
}
