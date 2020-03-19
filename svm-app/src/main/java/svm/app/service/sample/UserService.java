package svm.app.service.sample;

import svm.app.entity.sample.UserAttachment;
import svm.app.entity.sample.UserDO;

public interface UserService {
    Long saveUser(UserDO user);

    void updateUser(UserDO user);

    void deleteUsers(Long[] ids);

    UserDO getUser(Long id);

    Long saveAttachment(UserAttachment attachment);

    UserAttachment queryAttachment(Long id);
}
