package svm.security.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import svm.security.login.LoginConstants;
import svm.security.service.PermissionService;
import svm.security.session.SessionContext;
import svm.security.session.UserSession;
import svm.security.util.Token;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor extends BaseInterceptor {
    @Autowired
    PermissionService permissionService;

    @Autowired
    private Environment env;

    private static final String MESSAGE_NO_TOKEN = "未登录";

    private static final String MESSAGE_INVALID_TOKEN = "无效token";

    private static final String MESSAGE_TIMEOUT = "Session超时";

    long sessionTimeout;

    @PostConstruct
    private void init() {
        sessionTimeout = env.getProperty("app.sessionTimeout", Long.class);
        sessionTimeout = sessionTimeout * 60 * 1000;
    }

    @Override
    protected boolean doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(LoginConstants.HEADER_TOKEN);
        if(StringUtils.isEmpty(token)) {
            token = request.getParameter(LoginConstants.HEADER_TOKEN);
            if(StringUtils.isEmpty(token)) {
                writeMessage(response, MESSAGE_NO_TOKEN);
                return false;
            }
        }

        long userId, lastAccessTime;
        try {
            Token userToken = Token.parse(token);
            userId = userToken.getUserId();
            lastAccessTime = userToken.getLastAccessTime();
        } catch (Exception ex) {
            writeMessage(response, MESSAGE_INVALID_TOKEN);
            return false;
        }

        UserSession userSession = permissionService.getUserSession(userId);
        if(userSession == null || userSession.getUser() == null) {
            writeMessage(response, MESSAGE_INVALID_TOKEN);
            return false;
        }

        if(sessionTimeout > 0 && (System.currentTimeMillis() - lastAccessTime > sessionTimeout)) {
            writeMessage(response, MESSAGE_TIMEOUT);
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
