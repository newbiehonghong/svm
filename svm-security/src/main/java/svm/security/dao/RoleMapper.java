package svm.security.dao;

import svm.security.entity.Role;

public interface RoleMapper {
    void save(Role role);

    void update(Role role);

    void delete(Long id);

    Role get(Long id);
}