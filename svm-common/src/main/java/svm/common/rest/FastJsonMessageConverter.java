package svm.common.rest;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class FastJsonMessageConverter extends FastJsonHttpMessageConverter {
    @Override
    public void write(Object object, Type type, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        //ParameterizedType realType = new ParameterizedTypeImpl(new Type[]{type}, null, Result.class);
        //Object realObject = ConvertUtils.convert(object);
        super.write(object, type, contentType, outputMessage);
    }

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return super.read(type, contextClass, inputMessage);
    }
}
