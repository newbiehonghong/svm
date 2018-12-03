package svm.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import svm.security.interceptor.CorsInterceptor;
import svm.security.interceptor.PermissionInterceptor;
import svm.security.interceptor.TokenInterceptor;
import svm.security.login.interceptor.UserExistInterceptor;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    //跨域拦截器
    @Autowired
    private CorsInterceptor corsInterceptor;

    //<!--登录拦截器
    @Autowired
    private UserExistInterceptor userExistInterceptor;

    //-->

    //<!--组件拦截器
    @Autowired
    private TokenInterceptor tokenInterceptor;

    @Autowired
    private PermissionInterceptor permissionInterceptor;
    //-->

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        //跨域拦截器
        registry.addInterceptor(corsInterceptor)
                .excludePathPatterns("/static/*").addPathPatterns("/**");

        //登录拦截器
        registry.addInterceptor(userExistInterceptor).addPathPatterns("/login");

        //组件拦截器
        registry.addInterceptor(tokenInterceptor)
                .excludePathPatterns("/static/*").excludePathPatterns("/login")
                .addPathPatterns("/**");
//        registry.addInterceptor(permissionInterceptor)
//                .excludePathPatterns("/static/*").excludePathPatterns("/login")
//                .addPathPatterns("/**");
    }
}
