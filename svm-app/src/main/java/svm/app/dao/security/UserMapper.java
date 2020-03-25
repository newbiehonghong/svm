package svm.app.dao.security;

import org.apache.ibatis.annotations.Param;
import svm.app.entity.security.User;

import java.util.List;

public interface UserMapper {
    void save(User user);

    void update(User user);

    void updatePassword(@Param("id") Long id, @Param("password") String password);

    void delete(Long id);

    User get(Long id);

    User getUserByName(String name);

    List<User> queryAllUsers();
}