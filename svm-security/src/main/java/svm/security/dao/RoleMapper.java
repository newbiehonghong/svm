package svm.security.dao;

import svm.security.entity.Role;

import java.util.List;

public interface RoleMapper {
    void save(Role role);

    void update(Role role);

    void delete(String id);

    Role get(String id);

    List<Role> queryAllRoles();
}