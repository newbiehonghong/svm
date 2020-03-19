package svm.app.service.security;

import svm.app.common.session.UserSession;
import svm.app.entity.security.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionService {
    Permission savePermission(Permission permission);

    Permission updatePermission(Permission permission);

    void deletePermission(String id);

    List<String> queryByRoleId(String type, String roleId);

    void saveRolesMap(String permissionId, String[] roleIds);

    Set<String> getServicePermissionSet(Long userId);

    List<String> getPagePermissionList(Long userId);

    UserSession getUserSession(Long userId);
}
