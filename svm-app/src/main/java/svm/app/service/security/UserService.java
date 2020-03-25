package svm.app.service.security;

import svm.app.entity.security.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);

    User updateUser(User user);

    void updatePassword(Long id, String password);

    void deleteUser(Long id);

    User getUser(Long id);

    User getUserByName(String name);

    List<Long> queryByRoleId(String roleId);

    void saveRolesMap(Long userId, String[] roleIds);
}
