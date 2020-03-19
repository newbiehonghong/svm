package svm.app.common.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CorsInterceptor implements HandlerInterceptor {
    @Autowired
    private Environment environment;

    private static final String METHOD_OPTIONS = "OPTIONS";

    private String allowHeaders;

    private String exposeHeaders;

    private String origin;

    @PostConstruct
    private void init() {
        this.allowHeaders = environment.getProperty("svm.crossDomain.allowHeaders", "Accept,Content-Type,Origin,x-token,X-Requested-With,Authorization");
        this.exposeHeaders = environment.getProperty("svm.crossDomain.exposeHeaders", "");
        this.origin = environment.getProperty("svm.crossDomain.origin", "*");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "600");
        response.setHeader("Access-Control-Allow-Headers", allowHeaders);

        response.setHeader("Access-Control-Allow-Credentials", "true");
        //重放攻击监测如果客户端和服务器时间相差超过设定阈值，会返回服务器时间
        //跨域请求时，客户端默认只能取到服务器端response.headers上的这些属性：
        //Content-Language、Content-Type、Expires、Last-Modified、Pragma
        response.setHeader("Access-Control-Expose-Headers", exposeHeaders);

        response.setHeader("Access-Control-Allow-Origin", origin);

        return true;
    }
}
