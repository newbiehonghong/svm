package svm.app.service.security;

import svm.app.entity.security.Role;

import java.util.List;

public interface RoleService {
    Role saveRole(Role role);

    Role updateRole(Role role);

    void deleteRole(String id);

    Role getRole(String id);

    List<String> queryByUserId(Long userId);

    List<String> queryByPermissionId(String permissionId);

    void saveUsersMap(String roleId, Long[] userIds);

    void savePermissionsMap(String roleId, String[] permissionIds);
}
