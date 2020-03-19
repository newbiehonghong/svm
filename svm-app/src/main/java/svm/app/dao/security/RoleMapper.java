package svm.app.dao.security;

import svm.app.entity.security.Role;

import java.util.List;

public interface RoleMapper {
    void save(Role role);

    void update(Role role);

    void delete(String id);

    Role get(String id);

    List<Role> queryAllRoles();
}