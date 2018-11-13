package svm.security.service.impl;

import org.springframework.stereotype.Service;
import svm.security.dto.User;
import svm.security.service.UserService;

@Service("securityUserService")
public class UserServiceImpl implements UserService {
    @Override
    public User getUserByName(String name) {
        if(!("admin".equals(name) || "test".equals(name))) {
            return null;
        }

        User user = new User();

        user.setId(name);
        user.setName(name);
        user.setNickname(name);
        user.setPassword(name);

        return user;
    }
}
