package svm.security.dao;

import svm.security.entity.User;

public interface UserMapper {
    void save(User user);

    void update(User user);

    void delete(Long id);

    User get(Long id);

    User getUserByName(String name);
}