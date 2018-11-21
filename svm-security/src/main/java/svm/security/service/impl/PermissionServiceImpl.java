package svm.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import svm.security.dao.PermissionMapper;
import svm.security.entity.User;
import svm.security.service.PermissionService;
import svm.security.service.UserService;
import svm.security.session.UserSession;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("securityPermissionService")
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Override
    public Set<String> getServicePermissionSet(Long userId) {
        List<String> permissions = permissionMapper.getServicePermissions(userId);
        return new HashSet<>(permissions);
    }

    @Override
    public List<String> getPagePermissionList(Long userId) {
        return permissionMapper.getPagePermissions(userId);
    }

    @Override
    public UserSession getUserSession(String token) {
        String name = token;//TODO

        User user = userService.getUserByName(name);
        if(user == null) {
            return null;
        }

        UserSession userSession = new UserSession();
        userSession.setUser(user);
        return userSession;
    }
}
