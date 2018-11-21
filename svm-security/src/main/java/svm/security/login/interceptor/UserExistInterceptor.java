package svm.security.login.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import svm.security.entity.User;
import svm.security.interceptor.BaseInterceptor;
import svm.security.login.LoginConstants;
import svm.security.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserExistInterceptor extends BaseInterceptor {
    @Autowired
    private UserService userService;

    @Override
    protected boolean doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userName = request.getParameter(LoginConstants.PARAMETER_USER);
        User user = userService.getUserByName(userName);
        if(user == null) {
            writeMessage(response, "无效的用户名或密码");
            return false;
        }

        request.setAttribute(LoginConstants.ATTRIBUTE_USER, user);
        return true;
    }
}
