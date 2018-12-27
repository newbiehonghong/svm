package svm.security.service;

import svm.security.entity.User;

import java.util.List;

public interface UserService {
    Long saveUser(User user);

    void updateUser(User user);

    void deleteUser(Long id);

    User getUser(Long id);

    User getUserByName(String name);

    List<Long> queryByRoleId(String roleId);

    void saveRolesMap(Long userId, String[] roleIds);
}
