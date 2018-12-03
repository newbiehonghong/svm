package svm.security.service;

import com.github.pagehelper.PageInfo;
import svm.security.entity.User;

public interface UserService {
    Long saveUser(User user);

    void updateUser(User user);

    void deleteUser(Long id);

    User getUser(Long id);

    User getUserByName(String name);

    PageInfo<User> queryAllUsers(int pageNum, int pageSize);
}
