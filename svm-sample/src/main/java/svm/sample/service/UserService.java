package svm.sample.service;

import svm.sample.entity.UserAttachment;
import svm.sample.entity.UserDO;

public interface UserService {
    Long saveUser(UserDO user);

    void updateUser(UserDO user);

    void deleteUsers(Long[] ids);

    UserDO getUser(Long id);

    Long saveAttachment(UserAttachment attachment);

    UserAttachment queryAttachment(Long id);
}
