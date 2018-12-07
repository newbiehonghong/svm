package svm.security.dao;

import org.apache.ibatis.annotations.Param;
import svm.security.entity.Permission;

import java.util.List;

public interface PermissionMapper {
    void save(Permission permission);

    void update(Permission permission);

    void delete(String id);

    List<Permission> queryPermissions(@Param("type") String type);

    List<String> getServicePermissions(Long userId);

    List<String> getPagePermissions(Long userId);
}