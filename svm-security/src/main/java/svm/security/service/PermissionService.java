package svm.security.service;

import com.github.pagehelper.PageInfo;
import svm.security.entity.Permission;
import svm.security.session.UserSession;

import java.util.List;
import java.util.Set;

public interface PermissionService {
    void savePermission(Permission permission);

    void updatePermission(Permission permission);

    void deletePermission(String id);

    PageInfo<Permission> queryPermissions(String type, int pageNum, int pageSize);

    List<String> queryByRoleId(String roleId);

    void saveRolesMap(String permissionId, String[] roleIds);

    Set<String> getServicePermissionSet(Long userId);

    List<String> getPagePermissionList(Long userId);

    UserSession getUserSession(Long userId);
}
