package svm.common.rest;

import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import svm.common.exception.BusinessExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FastJsonWebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonMessageConverter fastJsonConverter = new FastJsonMessageConverter();

        com.alibaba.fastjson.support.config.FastJsonConfig fastJsonConfig = new com.alibaba.fastjson.support.config.FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(0, fastJsonConverter);
    }
}
