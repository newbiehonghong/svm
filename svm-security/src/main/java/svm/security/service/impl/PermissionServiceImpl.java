package svm.security.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import svm.security.dao.PermissionMapper;
import svm.security.dao.RolePermissionMapper;
import svm.security.entity.Permission;
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

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public void savePermission(Permission permission) {
        permissionMapper.save(permission);
    }

    @Override
    public void updatePermission(Permission permission) {
        permissionMapper.update(permission);
    }

    @Override
    public void deletePermission(String id) {
        permissionMapper.delete(id);
    }

    @Override
    public PageInfo<Permission> queryPermissions(String type, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Permission> permissions = permissionMapper.queryPermissions(type);
        return new PageInfo(permissions);
    }

    @Override
    public List<String> queryByRoleId(String roleId) {
        return rolePermissionMapper.queryPermissionsByRoleId(roleId);
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
