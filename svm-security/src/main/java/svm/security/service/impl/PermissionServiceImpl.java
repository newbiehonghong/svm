package svm.security.service.impl;

import org.springframework.stereotype.Service;
import svm.security.dto.User;
import svm.security.service.PermissionService;
import svm.security.session.UserSession;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("securityPermissionService")
public class PermissionServiceImpl implements PermissionService {
    @Override
    public Set<String> getServicePermissionSet(String userId) {
        Set<String> permissions = new HashSet<>();
        permissions.add("user/query");
        permissions.add("user/add");
        if("admin".equals(userId)) {
            permissions.add("user/delete");
        }
        return permissions;
    }

    @Override
    public List<String> getWebPermissionList(String userId) {
        List<String> permissions = new ArrayList<>();
        if("admin".equals(userId)) {
            permissions.add("sample/p_admin");
        } else {
            permissions.add("sample/p_test");
        }
        return permissions;
    }

    @Override
    public UserSession getUserSession(String token) {
        UserSession userSession = new UserSession();

        User user = new User();
        user.setId(token);
        user.setName(token);

        userSession.setUser(user);
        return userSession;
    }
}
