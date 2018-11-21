package svm.security.login.interceptor;

import org.springframework.stereotype.Component;
import svm.security.entity.User;
import svm.security.interceptor.BaseInterceptor;
import svm.security.login.LoginConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ComparePasswordInterceptor extends BaseInterceptor {
    @Override
    protected boolean doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String password = request.getParameter(LoginConstants.PARAMETER_PASSWORD);
        User user = (User) request.getAttribute(LoginConstants.ATTRIBUTE_USER);
        if(!password.equals(user.getPassword())) {
            writeMessage(response, "无效的用户名或密码");
            return false;
        }

        return true;
    }
}
