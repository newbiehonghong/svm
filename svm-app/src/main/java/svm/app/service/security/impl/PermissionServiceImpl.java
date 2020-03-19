package svm.app.service.security.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import svm.app.common.session.UserSession;
import svm.app.dao.security.PermissionMapper;
import svm.app.dao.security.RolePermissionMapper;
import svm.app.entity.security.Permission;
import svm.app.entity.security.User;
import svm.app.service.security.PermissionService;
import svm.app.service.security.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("securityPermissionService")
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public Permission savePermission(Permission permission) {
        permissionMapper.save(permission);
        return permission;
    }

    @Override
    public Permission updatePermission(Permission permission) {
        permissionMapper.update(permission);
        return permission;
    }

    @Override
    public void deletePermission(String id) {
        permissionMapper.delete(id);
    }

    @Override
    public List<String> queryByRoleId(String type, String roleId) {
        return rolePermissionMapper.queryPermissionsByRoleId(type, roleId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRolesMap(String permissionId, String[] roleIds) {
        rolePermissionMapper.deleteByPermissionId(permissionId);
        if(roleIds.length > 0) {
            rolePermissionMapper.savePermissionRoles(permissionId, roleIds);
        }
    }

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
    public UserSession getUserSession(Long userId) {
        User user = userService.getUser(userId);
        if(user == null) {
            return null;
        }

        UserSession userSession = new UserSession();
        userSession.setUser(user);
        return userSession;
    }
}
