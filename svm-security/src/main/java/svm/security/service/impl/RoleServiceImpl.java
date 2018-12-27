package svm.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import svm.security.dao.RoleMapper;
import svm.security.dao.RolePermissionMapper;
import svm.security.dao.UserRoleMapper;
import svm.security.entity.Role;
import svm.security.service.RoleService;

import java.util.List;

@Service("securityRoleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public void saveRole(Role role) {
        roleMapper.save(role);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.update(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(String id) {
        //删除角色对应的用户和权限项
        userRoleMapper.deleteByRoleId(id);
        rolePermissionMapper.deleteByRoleId(id);
        roleMapper.delete(id);
    }

    @Override
    public Role getRole(String id) {
        return roleMapper.get(id);
    }

    @Override
    public List<String> queryByUserId(Long userId) {
        return userRoleMapper.queryRolesByUserId(userId);
    }

    @Override
    public List<String> queryByPermissionId(String permissionId) {
        return rolePermissionMapper.queryRolesByPermissionId(permissionId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUsersMap(String roleId, Long[] userIds) {
        userRoleMapper.deleteByRoleId(roleId);
        if(userIds.length > 0) {
            userRoleMapper.saveRoleUsers(roleId, userIds);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void savePermissionsMap(String roleId, String[] permissionIds) {
        rolePermissionMapper.deleteByRoleId(roleId);
        if(permissionIds.length > 0) {
            rolePermissionMapper.saveRolePermissions(roleId, permissionIds);
        }
    }
}
