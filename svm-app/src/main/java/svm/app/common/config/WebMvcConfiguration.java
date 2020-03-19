package svm.app.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import svm.app.common.interceptor.CorsInterceptor;
import svm.app.common.interceptor.PermissionInterceptor;
import svm.app.common.interceptor.TokenInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    //跨域拦截器
    @Autowired
    private CorsInterceptor corsInterceptor;

    //<!--组件拦截器
    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Autowired
    private PermissionInterceptor permissionInterceptor;
    //-->

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //跨域拦截器
        registry.addInterceptor(corsInterceptor)
                .excludePathPatterns("/static/*").addPathPatterns("/**");

        //组件拦截器
//        registry.addInterceptor(tokenInterceptor)
//                .excludePathPatterns("/static/*").excludePathPatterns("/login")
//                .addPathPatterns("/**");
//        registry.addInterceptor(permissionInterceptor)
//                .excludePathPatterns("/static/*").excludePathPatterns("/login")
//                .addPathPatterns("/**");

    }
}
