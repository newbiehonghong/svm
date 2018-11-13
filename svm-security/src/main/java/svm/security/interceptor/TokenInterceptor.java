package svm.security.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import svm.security.login.LoginConstants;
import svm.security.service.PermissionService;
import svm.security.session.SessionContext;
import svm.security.session.UserSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor extends BaseInterceptor {
    @Autowired
    PermissionService permissionService;

    @Override
    protected boolean doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(LoginConstants.HEADER_TOKEN);
        if(StringUtils.isEmpty(token)) {
            token = request.getParameter(LoginConstants.HEADER_TOKEN);
            if(StringUtils.isEmpty(token)) {
                writeMessage(response, "未登录");
                return false;
            }
        }

        UserSession userSession = permissionService.getUserSession(token);
        if(userSession == null || userSession.getUser() == null) {
            writeMessage(response, "无效token");
            return false;
        }

        SessionContext.set(userSession);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SessionContext.clear();
    }
}
