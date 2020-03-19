package svm.app.common.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import svm.app.common.session.LoginConstants;
import svm.app.common.session.SessionContext;
import svm.app.common.session.Token;
import svm.app.common.session.UserSession;
import svm.app.service.security.PermissionService;
import svm.common.exception.BusinessException;

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
        sessionTimeout = env.getProperty("svm.sessionTimeout", Integer.class, 60);
        sessionTimeout = sessionTimeout * 60 * 1000L;
    }

    @Override
    protected boolean doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(LoginConstants.HEADER_TOKEN);
        if(StringUtils.isEmpty(token)) {
            token = request.getParameter(LoginConstants.HEADER_TOKEN);
            if(StringUtils.isEmpty(token)) {
                throw new BusinessException(401, MESSAGE_NO_TOKEN);
            }
        }

        long userId, lastAccessTime;
        try {
            Token userToken = Token.parse(token);
            userId = userToken.getUserId();
            lastAccessTime = userToken.getLastAccessTime();
        } catch (Exception ex) {
            throw new BusinessException(401, MESSAGE_INVALID_TOKEN);
        }

        UserSession userSession = permissionService.getUserSession(userId);
        if(userSession == null || userSession.getUser() == null) {
            throw new BusinessException(401, MESSAGE_INVALID_TOKEN);
        }

        if(sessionTimeout > 0 && (System.currentTimeMillis() - lastAccessTime > sessionTimeout)) {
            throw new BusinessException(400, MESSAGE_TIMEOUT);
        }

        SessionContext.set(userSession);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SessionContext.clear();
    }
}
