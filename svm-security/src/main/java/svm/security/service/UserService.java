package svm.security.service;

import svm.security.dto.User;

public interface UserService {
    User getUserByName(String name);
}
