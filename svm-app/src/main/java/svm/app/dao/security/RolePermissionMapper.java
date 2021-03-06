package svm.app.dao.security;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionMapper {
    List<String> queryRolesByPermissionId(@Param("permissionId") String permissionId);

    List<String> queryPermissionsByRoleId(@Param("type") String type, @Param("roleId") String roleId);

    void deleteByPermissionId(@Param("permissionId") String permissionId);

    void deleteByRoleId(@Param("roleId") String roleId);

    void deleteByTypeAndRoleId(@Param("permissionType") String permissionType, @Param("roleId") String roleId);

    void savePermissionRoles(@Param("permissionId") String permissionId, @Param("roleIds") String[] roleIds);

    void saveRolePermissions(@Param("roleId") String roleId, @Param("permissionIds") String[] permissionIds);
}