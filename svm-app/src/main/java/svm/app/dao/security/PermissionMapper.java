package svm.app.dao.security;

import org.apache.ibatis.annotations.Param;
import svm.app.entity.security.Permission;

import java.util.List;

public interface PermissionMapper {
    void save(Permission permission);

    void update(Permission permission);

    void delete(String id);

    List<Permission> queryPermissions(@Param("type") String type);

    List<String> getServicePermissions(Long userId);

    List<String> getPagePermissions(Long userId);
}