package svm.security.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletOutputStream;
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

    protected void writeMessage(HttpServletResponse response, String message) throws Exception {
        byte[] data = ("{\"error\":\"OK\",\"message\":\"" + message + "\"}").getBytes();
        response.setContentType("application/json; charset=utf-8");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(data);
        outputStream.flush();
    }

    protected abstract boolean doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
}
