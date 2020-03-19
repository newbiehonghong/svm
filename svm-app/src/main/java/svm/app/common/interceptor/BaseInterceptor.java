package svm.app.common.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseInterceptor implements HandlerInterceptor {
    private static final String METHOD_OPTIONS = "OPTIONS";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (METHOD_OPTIONS.equals(request.getMethod())) {
            return true;
        }

        return doPreHandle(request, response, handler);
    }

    protected abstract boolean doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}
