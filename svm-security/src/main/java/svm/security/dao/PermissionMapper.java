package svm.security.dao;

import svm.security.entity.Permission;
import svm.security.entity.User;

import java.util.List;

public interface PermissionMapper {
    void save(Permission permission);

    void update(Permission permission);

    void delete(String id);

    User get(String id);

    List<String> getServicePermissions(Long userId);

    List<String> getPagePermissions(Long userId);
}