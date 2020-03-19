package svm.app.common.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import svm.app.common.annotation.RequiredPermission;
import svm.app.common.session.SessionContext;
import svm.app.entity.security.User;
import svm.app.service.security.PermissionService;
import svm.common.exception.BusinessException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@Component
public class PermissionInterceptor extends BaseInterceptor {

    @Autowired
    private PermissionService permissionService;

    private static final String MESSAGE_NO_PERMISSION = "无权访问";

    @Override
    protected boolean doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = SessionContext.get().getUser();
        Long userId = user.getId();
        // 验证权限
        if (this.hasPermission(userId, handler)) {
            return true;
        }

        throw new BusinessException(401, MESSAGE_NO_PERMISSION);
    }

    private boolean hasPermission(Long userId, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法上的注解
            RequiredPermission requiredPermission = handlerMethod.getMethod().getAnnotation(RequiredPermission.class);
            // 如果方法上的注解为空 则获取类的注解
            if (requiredPermission == null) {
                requiredPermission = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RequiredPermission.class);
            }
            // 如果标记了注解，则判断权限
            if (requiredPermission != null && !StringUtils.isEmpty(requiredPermission.value())) {
                // redis或数据库 中获取该用户的权限信息 并判断是否有权限
                Set<String> permissionSet = permissionService.getServicePermissionSet(userId);
                if (CollectionUtils.isEmpty(permissionSet)) {
                    return false;
                }
                return permissionSet.contains(requiredPermission.value());
            }
        }
        return true;
    }
}