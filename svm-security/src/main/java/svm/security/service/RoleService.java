package svm.security.service;

import com.github.pagehelper.PageInfo;
import svm.security.entity.Role;

import java.util.List;

public interface RoleService {
    void saveRole(Role role);

    void updateRole(Role role);

    void deleteRole(String id);

    Role getRole(String id);

    PageInfo<Role> queryAllRoles(int pageNum, int pageSize);

    List<String> queryByUserId(Long userId);

    List<String> queryByPermissionId(String permissionId);

    void saveUsersMap(String roleId, Long[] userIds);

    void savePermissionsMap(String roleId, String[] permissionIds);
}
