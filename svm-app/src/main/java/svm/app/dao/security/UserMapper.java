package svm.app.dao.security;

import svm.app.entity.security.User;

import java.util.List;

public interface UserMapper {
    void save(User user);

    void update(User user);

    void delete(Long id);

    User get(Long id);

    User getUserByName(String name);

    List<User> queryAllUsers();
}